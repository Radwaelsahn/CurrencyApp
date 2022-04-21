package com.radwaelsahn.currencyapp.data.datasources.remote

import android.util.Log
import com.google.gson.Gson
import com.radwaelsahn.currencyapp.App
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
            val errorResponse = ErrorResponse("NO_INTERNET_CONNECTION")
            return errorResponse
        }
        return try {
            val response = responseCall.invoke()
            responseCall.toString()
            if (response.isSuccessful) {
                refreshCount = 0
//                Log.d("response", Gson().toJson(response.body()))
                response.body()
            } else {
                var requestUrl = ""
                var requestHeaders = ""
                var requestBody = ""
                var logMessage = ""
                try {
                    requestUrl = response.raw().request.url.toString()
                    logMessage = logMessage.plus(requestUrl).plus("\n")
                    requestHeaders = response.raw().request.headers.toString()
                    logMessage = logMessage.plus(requestHeaders).plus("\n")
                    requestBody =
                        if (response.raw().request.body != null) Gson().toJson(response.raw().request.body.toString()) else ""
                    logMessage.plus(requestBody).plus("\n")
                    logMessage = logMessage.plus(response.toString()).plus("\n")
                        .plus(response.errorBody().toString())
                } catch (ignore: Exception) {

                }

                val errorBody = response.errorBody()
                val error = errorBody?.string()!!

                try {
                    val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
                    errorResponse
                } catch (e: Exception) {
                    e.printStackTrace()

                    val errorResponse = ErrorResponse("please try again later")
                    errorResponse
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "::IO Exception + ${e.message.toString()}", e)
            val errorResponse = ErrorResponse("please try again later")
            errorResponse
        }
    }


}