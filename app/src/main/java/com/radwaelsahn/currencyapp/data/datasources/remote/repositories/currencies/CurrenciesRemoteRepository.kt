package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import android.util.Log
import com.google.gson.Gson
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse

import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse
import com.radwaelsahn.currencyapp.data.datasources.remote.BaseRemoteRepository
import com.radwaelsahn.currencyapp.data.models.Error
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import javax.inject.Inject

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class CurrenciesRemoteRepository @Inject constructor(
    private val service: CurrenciesService
) : CurrenciesRemoteSource, BaseRemoteRepository() {

    override suspend fun getCurrencies(key: String, base: String): Resource<CurrenciesResponse> {
        val response = processCall { service.getCurrencies(key) }
//        val response = processCall { service.getCurrenciesWithBase(key, "USD", "EGP") }
        // base API is restricted

        return try {
            var myResponse = response as CurrenciesResponse
            if (myResponse.success)
                Resource.Success(data = myResponse)
            else {
                Resource.DataError(errorResponse = ErrorResponse(myResponse.error as Error, false))
            }
        } catch (e: Exception) {
            Resource.DataError(errorResponse = (response as ErrorResponse))
        }
    }

    override suspend fun convertCurrency(
        accessKey: String, base: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse> {
        val response = processCall { service.convertCurrency(accessKey, base, to, amount) }
        //  API is restricted
        return try {
            var myResponse = response as ConvertResponse
            if (myResponse.success)
                Resource.Success(data = myResponse)
            else {
                Resource.DataError(errorResponse = ErrorResponse(myResponse.error as Error, false))
            }
        } catch (e: Exception) {
            Resource.DataError(errorResponse = (response as ErrorResponse))
        }
    }

    override suspend fun getHistory(
        date: String, accessKey: String,
        symbols: String, base: String
    ): Resource<CurrenciesResponse> {
        val response = processCall { service.getHistory(date, accessKey, symbols) }

        return try {
            var myResponse = response as CurrenciesResponse
            if (myResponse.success)
                Resource.Success(data = myResponse)
            else {
                Resource.DataError(errorResponse = ErrorResponse(myResponse.error as Error, false))
            }
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }
}