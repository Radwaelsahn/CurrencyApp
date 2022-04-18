package com.radwaelsahn.currencyapp.data.source.remote.repositories.characters

import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.source.local.LocalSource
import javax.inject.Inject


/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class CharactersDataRepository @Inject constructor(
    private val remoteRepository: CharactersRemoteRepository,
    private val localRepository: LocalSource
) : CharactersDataSource {

    override suspend fun saveCharacter(character: Character) {
        localRepository.saveCharacter(character)
    }

    override suspend fun getAllCharacters(): List<Character> {
        return localRepository.getAllCharacters()
    }


    /**Local**/

    /**    remote **/
    override suspend fun getMarvelCharacters(
        ts: String,
        apikey: String,
        hash: String, limit: Int, offset: Int
    ): Resource<BaseResponse<Character>> {
        return remoteRepository.getMarvelCharacters(ts, apikey, hash, limit, offset)
    }

    override suspend fun getCharacterDetails(
        characterId: Int, ts: String,
        apikey: String,
        hash: String
    ): Resource<BaseResponse<Character>> {
        return remoteRepository.getCharacterDetails(characterId, ts, apikey, hash)
    }

}