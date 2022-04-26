package com.radwaelsahn.currencyapp.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
import com.radwaelsahn.currencyapp.data.models.HistoryItem
//import com.radwaelsahn.currencyapp.data.models.Rates
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HistoryUseCase @Inject constructor(
    private val dataRepository: CurrenciesDataSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    //    private val _uiFlow = MutableStateFlow<Resource<Map<String, Double>>>(Resource.Loading(true))
//val uiFlow: StateFlow<Resource<Map<String, Double>>> = _uiFlow
    private val _uiFlow =
        MutableStateFlow<Resource<MutableList<HistoryItem>>>(Resource.Loading(true))
    val uiFlow: StateFlow<Resource<MutableList<HistoryItem>>> = _uiFlow


    val history = MutableLiveData<MutableList<HistoryItem>>()

    fun getRateOnDate(date: String, base: String, symbols: String) =
        async {
            dataRepository.getHistory(
                date,
                BuildConfig.API_KEY, symbols, base
            )
        }


    fun getHistoryAsync(base: String, symbols: String) {
        _uiFlow.value = Resource.Loading(true)

        launch {
            val tasks = listOf(
                getRateOnDate(getDayBeforeDateBy(3), base, symbols),
                getRateOnDate(getDayBeforeDateBy(2), base, symbols),
                getRateOnDate(getDayBeforeDateBy(1), base, symbols),
                getRateOnDate(getDayBeforeDateBy(0), base, symbols)
            )

            handleResponse(tasks.awaitAll())
            _uiFlow.value = Resource.Loading(false)
        }
    }

    private fun handleResponse(resources: List<Resource<CurrenciesResponse>>) {
        if (resources[resources.size - 1].errorResponse != null) {
            _uiFlow.value = Resource.Error(resources[resources.size - 1].errorResponse?.error?.info)
        } else if (resources[resources.size - 1]!!.data != null) {
            var list = history.value
            if (list == null)
                list = mutableListOf()

            for (resource in resources) {
                resource.data?.rates?.map {
                    list.add(
                        HistoryItem(
                            resource.data?.date!!,
                            it.key,
                            it.value.toString()
                        )
                    )
                }
            }

            history.value = list!!
            _uiFlow.value = Resource.Success(list!!)
        }
    }

    private fun getDayBeforeDateBy(by: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -by)
        val df = SimpleDateFormat("yyyy-MM-dd")
        val date = df.format(calendar.time)

        return date
    }

    fun callSingleGetHistoryApi(
        date: String,
        symbols: String, base: String
    ) {

        launch {
            try {
                _uiFlow.value = Resource.Loading(true)
                var resources = dataRepository.getHistory(
                    date,
                    BuildConfig.API_KEY, symbols, base
                )
                _uiFlow.value = Resource.Loading(false)
                if (resources.errorResponse != null) {
                    _uiFlow.value = Resource.Error(resources.errorResponse?.error?.info)
                } else if (resources!!.data != null) {

                    var list = history.value
                    if (list == null)
                        list = mutableListOf()

                    resources!!.data?.rates?.map {
                        list.add(
                            HistoryItem(
                                date,
                                it.key,
                                it.value.toString()
                            )
                        )
                    }
                    history.value = list!!
                    _uiFlow.value = Resource.Success(list!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiFlow.value = Resource.Loading(false)
                _uiFlow.value = Resource.Error(e.message)
            }
        }
    }
}