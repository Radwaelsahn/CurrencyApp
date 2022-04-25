package com.radwaelsahn.currencyapp.domain

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import com.radwaelsahn.currencyapp.data.models.Currency
import com.radwaelsahn.currencyapp.data.models.Rates
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetLatestCurrenciesUseCase @Inject constructor(
    private val dataRepository: CurrenciesDataSource,
    override val coroutineContext: CoroutineContext,
    private val currenciesMapper: CurrenciesMapper
) : CoroutineScope {


    val staticCurrenciesList = MutableLiveData<List<String>>()
    private val _uiFlow = MutableStateFlow<Resource<Map<String, Double>>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<Map<String, Double>>> = _uiFlow

    private val _response = MutableLiveData<Resource<CurrenciesResponse>>()
    val response = _response

    fun getStaticCurrencyList(input: InputStream) {

        try {
            val allText = input.bufferedReader().use(BufferedReader::readText)
            val countriesType = object : TypeToken<List<Currency>>() {}.type
            val list = Gson().fromJson<List<Currency>>(allText, countriesType).toMutableList()

            list?.let {
                var names = list.map { it.code }
                staticCurrenciesList.postValue(names)
            }

        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    fun fetchCurrencies(base: String) {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                var resources = dataRepository.getCurrencies(
                    BuildConfig.API_KEY, base
                )
                _uiFlow.value = Resource.Loading(false)

                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {
                    val rateMap = currenciesMapper.to(resources.data?.rates as Rates)
                    _response.postValue(resources)
                    _uiFlow.value = Resource.Success(rateMap)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)
                _uiFlow.value = Resource.Error(e.message)
            }
        }
    }
}