package com.radwaelsahn.currencyapp.presentation.converter

import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.data.models.Currency
import java.io.InputStream
import javax.inject.Inject

class ConverterViewModel @Inject constructor(private val converterUseCase: ConverterUseCase) :
    ViewModel() {

    fun getCurrencyList(input: InputStream): List<Currency> {
        return converterUseCase.getCurrencyList(input)
    }


}