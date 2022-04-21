package com.radwaelsahn.currencyapp.data.models.responses


data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val data: ResponseData<T>
)



