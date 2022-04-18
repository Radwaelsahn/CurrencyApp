package com.radwaelsahn.currencyapp.data.source

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse

/**
 * Created by Radwa Elsahn on 7/26/2020
 */

interface DataSource {
    /**local**/
    suspend fun saveCharacter(character: Character)
    suspend fun getAllCharacters(): List<Character>

    /**remote*/
    suspend fun getMarvelCharacters(
        ts: String,
        apikey: String,
        hash: String, limit: Int, offset: Int
    ): Resource<BaseResponse<Character>>

    suspend fun getCharacterDetails(
        characterId: Int, ts: String,
        apikey: String,
        hash: String
    ): Resource<BaseResponse<Character>>

}
