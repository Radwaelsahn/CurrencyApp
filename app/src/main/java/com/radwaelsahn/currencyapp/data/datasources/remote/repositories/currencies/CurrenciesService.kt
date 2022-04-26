package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.datasources.remote.networking.Urls
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Radwa Elsahn on 3/24/2020
 */

interface CurrenciesService {

    @GET(Urls.Currencies)
    suspend fun getCurrenciesRate(
        @Query("access_key") accessKey: String
    ): Response<CurrenciesResponse>

    @GET(Urls.Currencies)
    suspend fun getCurrenciesWithBase(
        @Query("access_key") accessKey: String,
        @Query("base") base: String,
//        latest?access_key=5446d9ab83f7d41d97d4ec800a45b362&symbols=EGP
    ): Response<CurrenciesResponse>

    @GET(Urls.Currencies)
    suspend fun getCurrenciesWithSymbols(
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String
    ): Response<CurrenciesResponse>

    @GET(Urls.Convert)
    suspend fun convertCurrency(
        @Query("access_key") accessKey: String, @Query("from") base: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): Response<ConvertResponse>

    @GET(Urls.History)
    //2021-04-20?access_key=5446d9ab83f7d41d97d4ec800a45b362&symbols=USD,CAD,EUR,EGP
    suspend fun getHistory(
        @Path("dateFrom") date: String, @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String
    ): Response<CurrenciesResponse>
}
