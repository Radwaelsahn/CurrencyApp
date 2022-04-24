package com.radwaelsahn.currencyapp.presentation.converter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    val uiFlowGet = getLatestCurrenciesUseCase.uiFlow
    val currenciesResponse = getLatestCurrenciesUseCase.response

    val uiFlowConvert = converterUseCase.uiFlow
    val convertedValue = MutableLiveData<String>()
    var inputValue = "1"

    val staticCurrenciesList = getLatestCurrenciesUseCase.staticCurrenciesList

    val selectFrom = MutableLiveData<Int>()
    val selectTo = MutableLiveData<Int>()

    fun getStaticCurrencyList(input: InputStream) {
        getLatestCurrenciesUseCase.getStaticCurrencyList(input)
    }

    private fun fetchCurrencies() {
        getLatestCurrenciesUseCase.fetchCurrencies()
    }

    fun getData() {
        fetchCurrencies()
    }

    fun convertCurrency(from: String, to: String, amount: String) {
        if (isValidAmount(amount))
            converterUseCase.convertCurrency(from, to, amount)
    }


    fun onValueChanged(from: String, to: String, amount: String) {
        convertCurrency(from, to, amount)
    }

    fun swap(from: String, to: String, amount: String) {
        inputValue = (amount)

        val fromPosition = getCurrencyIndex(from)
        val toPosition = getCurrencyIndex(to)
        selectFrom.postValue(toPosition)
        selectTo.postValue(fromPosition)
    }

    private fun getCurrencyIndex(text: String): Int {
        Log.e("position", text)
        val position = staticCurrenciesList.value?.indexOf(text) ?: return 0
        Log.e("position", position.toString())
        return position

    }

    private fun isValidAmount(amount: String): Boolean {
        if (amount.isEmpty())
            return false
        return amount.toIntOrNull() != null


    }

}