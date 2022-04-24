package com.radwaelsahn.currencyapp.data.models.responses

import com.radwaelsahn.currencyapp.data.models.Error


open class BaseResponse {
    val success: Boolean = false
    val error: Error? = null
}



