package com.radwaelsahn.currencyapp.data.source.remote.repositories.characters

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character

import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse
import com.radwaelsahn.currencyapp.data.source.remote.BaseRemoteRepository
import javax.inject.Inject

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class CharactersRemoteRepository @Inject constructor(
    val service: CharactersService
) : CharactersRemoteSource, BaseRemoteRepository() {

    override suspend fun
            getMarvelCharacters(
        ts: String,
        apikey: String,
        hash: String,
        limit: Int, offset: Int

    ): Resource<BaseResponse<Character>> {
//        val service = ServiceGenerator.createService(CharactersService::class.java)
        val response = processCall { service.getMarvelCharacters(ts, apikey, hash, limit, offset) }

        return try {
            var myResponse = response as BaseResponse<Character>
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getCharacterDetails(
        characterId: Int, ts: String,
        apikey: String,
        hash: String
    ): Resource<BaseResponse<Character>> {
//        val service = ServiceGenerator.createService(getCharacterDetailsService::class.java)
        val response = processCall { service.getCharacterDetails(characterId, ts, apikey, hash) }

        return try {
            var myResponse = response as BaseResponse<Character>
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

}