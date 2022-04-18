package com.radwaelsahn.currencyapp.data.source.remote.networking.services

import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.source.remote.networking.Urls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Radwa Elsahn on 3/24/2020
 */

interface getCharacterDetailsService {
    @GET(Urls.CHARACTER_DETAILS)
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Response<BaseResponse<Character>>

}
