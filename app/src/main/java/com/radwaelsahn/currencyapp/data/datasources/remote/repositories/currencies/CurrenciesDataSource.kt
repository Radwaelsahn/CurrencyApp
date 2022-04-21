package com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.ConvertResponse
import com.radwaelsahn.currencyapp.data.models.CurrenciesResponse

/**
 * Created by Radwa Elsahn on 7/26/2020
 */

interface CurrenciesDataSource {
    /**local**/
    suspend fun saveCharacter(character: Character)
    suspend fun getAllCharacters(): List<Character>

    /**remote*/
    suspend fun getCurrencies(key: String): Resource<CurrenciesResponse>
    suspend fun convertCurrency(
        accessKey: String, from: String,
        to: String,
        amount: String
    ): Resource<ConvertResponse>
}
