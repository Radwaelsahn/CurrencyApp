package com.radwaelsahn.currencyapp.data.models.responses

import com.radwaelsahn.currencyapp.data.models.Info
import com.radwaelsahn.currencyapp.data.models.Query

data class ConvertResponse(
    val query: Query?,
    val info: Info?,
    val historical: String?,
    val date: String?,
    val result: Double?, //3724.305775
):BaseResponse()