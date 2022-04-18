package com.radwaelsahn.currencyapp.data.source.remote

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

interface RemoteSource {
    suspend fun getMarvelCharacters(
        ts: String,
        apikey: String,
        hash: String,limit: Int, offset: Int
    ): Resource<BaseResponse<Character>>

    suspend fun getCharacterDetails(
        characterId: Int, ts: String,
        apikey: String,
        hash: String
    ): Resource<BaseResponse<Character>>

}
