package com.radwaelsahn.currencyapp.domain

import androidx.lifecycle.MutableLiveData
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConverterUseCase @Inject constructor(
    private val dataRepository: CurrenciesDataSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    val isLoading = MutableLiveData<Boolean>()
    private val _uiFlow = MutableStateFlow<Resource<ConvertResponse>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<ConvertResponse>> = _uiFlow

    private val _response = MutableLiveData<Resource<ConvertResponse>>()
    val response = _response

    fun convertCurrency(from: String, to: String, amount: String) {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                isLoading.value = true
                var resources = dataRepository.convertCurrency(
                    BuildConfig.API_KEY, from, to, amount
                )
                _uiFlow.value = Resource.Loading(false)
                isLoading.value = false
                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {
                    _response.postValue(resources)
                    _uiFlow.value = Resource.Success(resources.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)
                isLoading.value = false
                _uiFlow.value = Resource.Error(e.message)
            }
        }
    }

}