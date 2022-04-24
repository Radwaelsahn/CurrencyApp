package com.radwaelsahn.currencyapp.data.models.responses

import com.radwaelsahn.currencyapp.data.models.Rates

data class CurrenciesResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val timestamp: Int
):BaseResponse()