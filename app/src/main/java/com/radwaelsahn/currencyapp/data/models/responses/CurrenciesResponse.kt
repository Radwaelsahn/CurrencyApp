package com.radwaelsahn.currencyapp.data.models.responses

data class CurrenciesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val timestamp: Int
) : BaseResponse()