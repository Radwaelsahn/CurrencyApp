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
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    fun getStaticCurrencyList(input: InputStream): List<Currency>? {

        try {
            val allText = input.bufferedReader().use(BufferedReader::readText)
            println("CURRENCY: " + allText)
            val countriesType = object : TypeToken<List<Currency>>() {}.type
            val list = Gson().fromJson<List<Currency>>(allText, countriesType).toMutableList()
            println("RADWA: HERE1")
            return list
        } catch (e: Exception) {
            println("RADWA: HERE2")
            println("RADWA: " + e.message)
            return null
        }
        println("RADWA: HERE3")
    }

    val isLoading = MutableLiveData<Boolean>()
    private val _uiFlow = MutableStateFlow<Resource<Rates>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<Rates>> = _uiFlow

    private val _response = MutableLiveData<Resource<CurrenciesResponse>>()
    val response = _response

    fun fetchCurrencies() {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                isLoading.value = true
                var resources = dataRepository.getCurrencies(
                    BuildConfig.API_KEY
                )
                _uiFlow.value = Resource.Loading(false)
                isLoading.value = false

                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {

                    _response.postValue(resources)
                    _uiFlow.value = Resource.Success(resources.data?.rates)
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