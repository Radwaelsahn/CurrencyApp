package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Radwa Elsahn on 7/26/2020
 */

interface CurrenciesDataSource {

    /**remote*/
    suspend fun getCurrenciesRate(key: String, base: String): Resource<CurrenciesResponse>
    suspend fun convertCurrency(
        accessKey: String, base: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse>

    suspend fun getHistory(
        date: String, accessKey: String,
        symbols: String, base: String
    ): Resource<CurrenciesResponse>
}
