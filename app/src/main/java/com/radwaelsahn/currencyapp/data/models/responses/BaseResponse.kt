package com.radwaelsahn.currencyapp.data.models.responses

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: ResponseData<T>
)



