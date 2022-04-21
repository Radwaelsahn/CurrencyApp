package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.datasources.remote.networking.Urls
import com.radwaelsahn.currencyapp.data.models.ConvertResponse
import com.radwaelsahn.currencyapp.data.models.CurrenciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Radwa Elsahn on 3/24/2020
 */

interface CurrenciesService {

    @GET(Urls.Currencies)
    suspend fun getCurrencies(
        @Query("access_key") accessKey: String
    ): Response<CurrenciesResponse>

    @GET(Urls.Convert)
    suspend fun convertCurrency(
        @Query("access_key") accessKey: String, @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): Response<ConvertResponse>
}
