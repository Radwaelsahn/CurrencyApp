package com.radwaelsahn.currencyapp.data.datasources.remote

import android.util.Log
import com.google.gson.Gson
import com.radwaelsahn.currencyapp.App
import com.radwaelsahn.currencyapp.data.models.Error
import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse
import com.radwaelsahn.currencyapp.utils.Network
import retrofit2.Response
import java.io.IOException


/**
 * Created by Radwa Elsahn on 7/7/2020
 */

open class BaseRemoteRepository {
     val TAG = this.javaClass.simpleName
    var refreshCount = 0
    suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!Network.isConnected(App.context)) {
            val errorResponse = ErrorResponse(Error(0,"NO_INTERNET_CONNECTION",""),false)
            return errorResponse
        }
        return try {
            val response = responseCall.invoke()
            responseCall.toString()
            if (response.isSuccessful) {
                refreshCount = 0
                Log.e("response", Gson().toJson(response.body()))
                response.body()
            } else {
                val errorBody = response.errorBody()
                val error = errorBody?.string()!!

                try {
                    val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
                    errorResponse
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("EXEP",e.message.toString())
                    val errorResponse = ErrorResponse(Error(0,"Please Try Again Later",""),false)
                    errorResponse
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "::IO Exception + ${e.message.toString()}", e)
            val errorResponse = ErrorResponse(Error(0,"Please Try Again Later",""),false)
            errorResponse
        }
    }


}