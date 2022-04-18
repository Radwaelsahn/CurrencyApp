package com.radwaelsahn.currencyapp.data.source.local

import android.content.Context
import android.util.Log
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.source.local.db.MarvelDatabase
import com.radwaelsahn.currencyapp.data.source.local.mapper.CharacterLocalMapper
import javax.inject.Inject


/**
 * Created by Radwa Elsahn on 3/3/2022
 */

class LocalRepository @Inject constructor(
    val context: Context,
    val charatersMapper: CharacterLocalMapper
) : LocalSource {

    override suspend fun saveCharacter(character: Character) {
        val characterDao = MarvelDatabase.getInstance(context)?.getCharactersDao()
        var id = characterDao?.insertReplace(charatersMapper.to(character))
        Log.e("saveCharacter", id.toString())
    }

    override suspend fun getAllCharacters(): List<Character> {
//        val db = AppDatabase.getAppDataBase(App.context)
//        var characters = db?.CharactersDao()?.getAll()!!
        val characterDao = MarvelDatabase.getInstance(context)?.getCharactersDao()
        var characters = characterDao?.getAll()!!

        var list = mutableListOf<Character>()
        characters.map { list.add(charatersMapper.from(it)) }

        return list
    }


}
