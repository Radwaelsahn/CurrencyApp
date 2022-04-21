package com.radwaelsahn.currencyapp.data.models

class ConvertResponse(
    val success: Boolean,
    val query: Query,
    val info: Info,
    val historical: String,
    val date: String,
    val result : Double //3724.305775

)