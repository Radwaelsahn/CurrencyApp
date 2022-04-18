package com.radwaelsahn.currencyapp.data.source.local

import com.radwaelsahn.currencyapp.data.models.Character

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

interface LocalSource {

    suspend fun saveCharacter(character: Character)
    suspend fun getAllCharacters(): List<Character>

}
