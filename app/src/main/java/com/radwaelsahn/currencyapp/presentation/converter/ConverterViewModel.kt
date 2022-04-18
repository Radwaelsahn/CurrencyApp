package com.radwaelsahn.currencyapp.presentation.converter

import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.data.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val converterUseCase: ConverterUseCase) :
    ViewModel() {

    fun getCurrencyList(input: InputStream): List<Currency>? {
        return converterUseCase.getCurrencyList(input)
    }

    fun return1():Int
    {
        return 1
    }


}