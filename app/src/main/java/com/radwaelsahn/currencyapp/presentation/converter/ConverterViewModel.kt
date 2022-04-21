package com.radwaelsahn.currencyapp.presentation.converter

import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.data.models.Currency
import com.radwaelsahn.currencyapp.domain.ConverterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val converterUseCase: ConverterUseCase) : ViewModel() {
    val uiFlow = converterUseCase.uiFlow
    val isLoading = converterUseCase.isLoading
    val response = converterUseCase.response

    fun getCurrencyList(input: InputStream): List<Currency>? {
        return converterUseCase.getCurrencyList(input)
    }

    private fun fetchCurrencies() {
        converterUseCase.getCurrencies()
    }

    fun return1(): Int {
        return 1
    }


    fun getData() {
        fetchCurrencies()
    }


}