package com.radwaelsahn.currencyapp.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.radwaelsahn.currencyapp.App
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.data.models.Character
import com.radwaelsahn.currencyapp.data.models.responses.BaseResponse
import com.radwaelsahn.currencyapp.data.models.responses.ErrorResponse
import com.radwaelsahn.currencyapp.data.source.remote.networking.ServiceGenerator
import com.radwaelsahn.currencyapp.data.source.remote.networking.services.CharactersService
import com.radwaelsahn.currencyapp.data.source.remote.networking.services.getCharacterDetailsService
import com.radwaelsahn.currencyapp.utils.Network
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class RemoteRepository @Inject constructor() : RemoteSource {

    private val TAG = this.javaClass.simpleName

    var refreshCount = 0
    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
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

    override suspend fun
            getMarvelCharacters(
        ts: String,
        apikey: String,
        hash: String,
        limit: Int, offset: Int

    ): Resource<BaseResponse<Character>> {
        val service = ServiceGenerator.createService(CharactersService::class.java)
        val response = processCall { service.getMarvelCharacters(ts, apikey, hash, limit, offset) }

        return try {
            var myResponse = response as BaseResponse<Character>
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }

    override suspend fun getCharacterDetails(
        characterId: Int, ts: String,
        apikey: String,
        hash: String
    ): Resource<BaseResponse<Character>> {
        val service = ServiceGenerator.createService(getCharacterDetailsService::class.java)
        val response = processCall { service.getCharacterDetails(characterId, ts, apikey, hash) }

        return try {
            var myResponse = response as BaseResponse<Character>
            Resource.Success(data = myResponse)
        } catch (e: Exception) {
            Resource.DataError(errorResponse = response as ErrorResponse)
        }
    }




}