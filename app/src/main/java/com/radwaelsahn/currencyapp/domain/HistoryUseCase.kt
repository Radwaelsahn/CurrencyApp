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

            var resources1Deferred = getRateOnDate(getDayBeforeDateBy(3), base, symbols)

            var resources2Deferred = getRateOnDate(getDayBeforeDateBy(2), base, symbols)

            var resources3Deferred = getRateOnDate(getDayBeforeDateBy(1), base, symbols)

            var resources4Deferred = getRateOnDate(getDayBeforeDateBy(0), base, symbols)

            handleResponse (resources1Deferred.await(), resources2Deferred.await(), resources3Deferred.await(), resources4Deferred.await())
            _uiFlow.value = Resource.Loading(false)

        }
    }

    private fun handleResponse(resources1: Resource<CurrenciesResponse>, resources2: Resource<CurrenciesResponse>, resources3: Resource<CurrenciesResponse>, resources4: Resource<CurrenciesResponse>) {

        if (resources4.errorResponse != null) {
            _uiFlow.value = Resource.Error(resources4.errorResponse?.error?.info)
        } else if (resources4!!.data != null) {

            var list = history.value
            if (list == null)
                list = mutableListOf()

            resources1!!.data?.rates?.map {
                list.add(
                    HistoryItem(
                        resources1.data?.date!!,
                        it.key,
                        it.value.toString()
                    )
                )
            }

            resources2!!.data?.rates?.map {
                list.add(
                    HistoryItem(
                        resources2.data?.date!!,
                        it.key,
                        it.value.toString()
                    )
                )
            }

            resources3!!.data?.rates?.map {
                list.add(
                    HistoryItem(
                        resources3.data?.date!!,
                        it.key,
                        it.value.toString()
                    )
                )
            }

            resources4!!.data?.rates?.map {
                list.add(
                    HistoryItem(
                        resources4.data?.date!!,
                        it.key,
                        it.value.toString()
                    )
                )
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