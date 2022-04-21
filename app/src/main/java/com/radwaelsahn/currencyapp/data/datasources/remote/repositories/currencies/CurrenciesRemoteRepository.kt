package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.CurrenciesResponse

import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse
import com.radwaelsahn.currencyapp.data.datasources.remote.BaseRemoteRepository
import com.radwaelsahn.currencyapp.data.models.ConvertResponse
import javax.inject.Inject

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class CurrenciesRemoteRepository @Inject constructor(
    private val service: CurrenciesService
) : CurrenciesRemoteSource, BaseRemoteRepository() {

    override suspend fun getCurrencies(key: String): Resource<CurrenciesResponse> {
        val response = processCall { service.getCurrencies(key) }

        return try {
            var myResponse = response as CurrenciesResponse
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun convertCurrency(
        accessKey: String, from: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse> {
        val response = processCall { service.convertCurrency(accessKey, from, to, amount) }

        return try {
            var myResponse = response as ConvertResponse
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }
}