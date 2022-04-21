package com.radwaelsahn.currencyapp.presentation.converter

import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.data.models.Currency
import com.radwaelsahn.currencyapp.domain.ConverterUseCase
import com.radwaelsahn.currencyapp.domain.GetLatestCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val converterUseCase: ConverterUseCase,
    private val getLatestCurrenciesUseCase: GetLatestCurrenciesUseCase
) : ViewModel() {
//    val uiFlow = getLatestCurrenciesUseCase.uiFlow
//    val isLoading = getLatestCurrenciesUseCase.isLoading

    val currenciesResponse = getLatestCurrenciesUseCase.response

    val uiFlow = converterUseCase.uiFlow
    val isLoading = converterUseCase.isLoading
    val convertResponse = converterUseCase.response

    fun getCurrencyList(input: InputStream): List<Currency>? {
        return getLatestCurrenciesUseCase.getStaticCurrencyList(input)
    }

    private fun fetchCurrencies() {
        getLatestCurrenciesUseCase.fetchCurrencies()
    }

    fun return1(): Int {
        return 1
    }

    fun getData() {
        //fetchCurrencies()
    }

    fun convertCurrency(from: String, to: String, amount: String) {
        converterUseCase.convertCurrency(from, to, amount)
    }


}