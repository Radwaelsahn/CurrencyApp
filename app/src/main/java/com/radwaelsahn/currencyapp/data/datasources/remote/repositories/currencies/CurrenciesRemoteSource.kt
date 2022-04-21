package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.CurrenciesResponse

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

interface CurrenciesRemoteSource {
    suspend fun getCurrencies(key: String): Resource<CurrenciesResponse>
}
