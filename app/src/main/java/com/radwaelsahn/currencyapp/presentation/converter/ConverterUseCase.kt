package com.radwaelsahn.currencyapp.presentation.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radwaelsahn.currencyapp.data.models.Currency
import com.radwaelsahn.currencyapp.data.source.remote.repositories.characters.CharactersDataSource
import kotlinx.coroutines.CoroutineScope
import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConverterUseCase
@Inject
constructor(
    private val dataSource: CharactersDataSource,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    fun getCurrencyList(input: InputStream): List<Currency>? {

        try {
            val allText = input.bufferedReader().use(BufferedReader::readText)
            println("CURRENCY: " + allText)
            val countriesType = object : TypeToken<List<Currency>>() {}.type
            val list =  Gson().fromJson<List<Currency>>(allText, countriesType).toMutableList()
            println("RADWA: HERE1")
            return list
        } catch (e: Exception) {
            println("RADWA: HERE2")
            println("RADWA: " + e.message)
            return null
        }
        println("RADWA: HERE3")
    }

}