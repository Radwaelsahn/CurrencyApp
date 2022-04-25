package com.radwaelsahn.currencyapp.presentation.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radwaelsahn.currencyapp.domain.HistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: HistoryUseCase
) : ViewModel() {
    val uiFlowGet = getHistoryUseCase.uiFlow

    var base = ""
    var to = ""

    fun getHistory(base: String, to: String) {
        Log.e("from",base + " to: " +to)

        getHistoryUseCase.getHistory(base, to)
    }


}