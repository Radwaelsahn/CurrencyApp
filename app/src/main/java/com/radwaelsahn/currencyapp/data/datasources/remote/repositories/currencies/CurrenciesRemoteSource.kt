package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

interface CurrenciesRemoteSource {
    suspend fun getCurrencies(key: String): Resource<CurrenciesResponse>
    suspend fun convertCurrency(
        accessKey: String, from: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse>
}
