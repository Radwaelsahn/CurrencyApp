package com.radwaelsahn.currencyapp.domain

import androidx.lifecycle.MutableLiveData
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
import com.radwaelsahn.currencyapp.data.models.Currencies
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

    private val _uiFlow = MutableStateFlow<Resource<String>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<String>> = _uiFlow
    val convertedValue = MutableLiveData<String>()
    fun callConvertCurrencyApi(base: String, to: String, amount: String) {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                var resources = dataRepository.convertCurrency(
                    BuildConfig.API_KEY, base, to, amount
                )
                _uiFlow.value = Resource.Loading(false)
                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                    // we're using convert static bcoz it's restricted on the APIs
                    _uiFlow.value = Resource.Success(convertCurrencyStatic(base, to, amount))
                } else if (resources!!.data != null) {
                    _uiFlow.value = Resource.Success(resources.data?.result.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)
                _uiFlow.value = Resource.Error(e.message)
            }
        }
    }

    fun convertCurrencyFromLatestApi(base: String, to: String, amount: String) {
        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                var resources = dataRepository.getCurrenciesRate(
                    BuildConfig.API_KEY, base
                )
                _uiFlow.value = Resource.Loading(false)

                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {
//                    val rateMap = currenciesMapper.to(resources.data?.rates as Rates)
                    val intAmount = Integer.parseInt(amount)
                    resources.data?.let { data ->
                        data.rates?.let { rates->
                            if(rates.isNotEmpty()) {
                                val rate = rates.get(to)!!
                                val result = (intAmount * rate).toString()
                                convertedValue.postValue(result)
                                _uiFlow.value = Resource.Success(result)
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)

                _uiFlow.value = Resource.Error(e.message)
            }

        }
    }

    private fun convertCurrencyStatic(base: String, to: String, amount: String): String {
        val intAmount = Integer.parseInt(amount)
        var rate = 1.0
        rate = when (to) { // will consider the base is
            Currencies.USD.name -> Currencies.USD.rate
            Currencies.AED.name -> Currencies.AED.rate
            Currencies.CAD.name -> Currencies.CAD.rate
            Currencies.EGP.name -> Currencies.EGP.rate
            Currencies.EUR.name -> Currencies.EUR.rate
            Currencies.KWD.name -> Currencies.KWD.rate
            else -> Currencies.USD.rate
        }

        return (intAmount * rate).toString()
    }


}