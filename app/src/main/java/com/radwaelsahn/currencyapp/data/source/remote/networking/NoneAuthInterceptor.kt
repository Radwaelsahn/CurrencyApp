package com.radwaelsahn.currencyapp.data.source.remote.networking

import com.radwaelsahn.currencyapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoneAuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorisedRequestBuilder = originalRequest.newBuilder().apply {
            header(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE)
            header("Accept", Constants.CONTENT_TYPE_VALUE)
            header("Content-type", "json")
            method(originalRequest.method, originalRequest.body)
        }
        return chain.proceed(authorisedRequestBuilder.build())
    }


}
