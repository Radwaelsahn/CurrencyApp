package com.radwaelsahn.currencyapp.domain

import androidx.lifecycle.MutableLiveData
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
//import com.radwaelsahn.currencyapp.data.models.Rates
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HistoryUseCase @Inject constructor(
    private val dataRepository: CurrenciesDataSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    private val _uiFlow = MutableStateFlow<Resource<Map<String, Double>>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<Map<String, Double>>> = _uiFlow

    private val _response = MutableLiveData<Resource<CurrenciesResponse>>()
    val response = _response


    fun callGetHistoryApi(
        date: String,
        symbols: String, base: String
    ) {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                var resources = dataRepository.getHistory(
                    date,
                    BuildConfig.API_KEY, symbols, base
                )
                _uiFlow.value = Resource.Loading(false)
                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {
//                    val rateMap = currenciesMapper.to(resources.data?.rates as Rates)
                    _response.postValue(resources)
//                    _uiFlow.value = Resource.Success(rateMap)

                    //currenciesList.postValue(resources.data?.rates?.keys?.toList())
                    _uiFlow.value = Resource.Success(resources.data?.rates)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)
                _uiFlow.value = Resource.Error(e.message)
            }
        }
    }

}