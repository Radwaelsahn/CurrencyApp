package com.radwaelsahn.currencyapp.presentation.converter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.domain.GetLatestCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class StaticConverterViewModel @Inject constructor(
    private val getLatestCurrenciesUseCase: GetLatestCurrenciesUseCase
) : ViewModel() {
    val uiFlowGet = getLatestCurrenciesUseCase.uiFlow
    val currenciesResponse = getLatestCurrenciesUseCase.response

    val fromValue = MutableLiveData<String>()
    var inputValue = "1"

    val staticCurrenciesList = getLatestCurrenciesUseCase.currenciesList

    val selectFrom = MutableLiveData<Int>()
    val selectTo = MutableLiveData<Int>()

    fun getStaticCurrencyList(input: InputStream) {
        getLatestCurrenciesUseCase.getStaticCurrencyList(input)
    }

    private fun fetchCurrencies(base: String) {
        getLatestCurrenciesUseCase.fetchCurrencies(base)
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