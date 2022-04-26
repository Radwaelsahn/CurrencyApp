package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.CurrenciesResponse
import com.radwaelsahn.currencyapp.data.models.responses.ConvertResponse
import javax.inject.Inject


/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class CurrenciesDataRepository @Inject constructor(
    private val remoteRepository: CurrenciesRemoteRepository
) : CurrenciesDataSource {
    /**    remote **/
    override suspend fun getCurrenciesRate(key: String, base: String): Resource<CurrenciesResponse> {
        return remoteRepository.getCurrenciesRate(key, base)
    }

    override suspend fun convertCurrency(
        accessKey: String,
        base: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse> {
        return remoteRepository.convertCurrency(accessKey, base, to, amount)
    }

    override suspend fun getHistory(
        date: String, accessKey: String,
        symbols: String, base: String
    ): Resource<CurrenciesResponse> {

        return remoteRepository.getHistory(date, accessKey, symbols, base)
    }


}