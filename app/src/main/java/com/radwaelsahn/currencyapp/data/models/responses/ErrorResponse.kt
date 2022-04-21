package com.radwaelsahn.currencyapp.data.models.responses

import com.radwaelsahn.currencyapp.data.models.Error

data class ErrorResponse(
    val error: Error,
    val success: Boolean
)