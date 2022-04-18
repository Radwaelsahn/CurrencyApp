package com.radwaelsahn.currencyapp.data.models

data class Currency(
    val code: String,
    val decimal_digits: Int,
    val name: String,
    val name_plural: String,
    val rounding: Int,
    val symbol: String,
    val symbol_native: String
)