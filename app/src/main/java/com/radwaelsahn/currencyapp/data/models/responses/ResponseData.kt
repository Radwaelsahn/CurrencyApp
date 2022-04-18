package com.radwaelsahn.currencyapp.data.models.responses

data class ResponseData<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)