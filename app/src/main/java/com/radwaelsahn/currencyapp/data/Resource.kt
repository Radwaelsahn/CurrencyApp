package com.radwaelsahn.currencyapp.data

import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse

sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val errorResponse: ErrorResponse? = null
) {

    class Success<T>(data: T?) : Resource<T>(data)
    class DataError<T>(errorResponse: ErrorResponse) : Resource<T>(errorResponse = errorResponse)
    class Loading<T>(show: Boolean) : Resource<T>(loading = show)
    class Error<T>(message: String?) : Resource<T>(error = message)
}