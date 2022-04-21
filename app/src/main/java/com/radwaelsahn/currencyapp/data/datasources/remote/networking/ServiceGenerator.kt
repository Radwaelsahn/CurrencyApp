package com.radwaelsahn.currencyapp.data.datasources.remote.networking

import com.google.gson.Gson
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.datasources.local.Session
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Radwa Elsahn on 3/21/2020
 */

@Singleton
class ServiceGenerator
@Inject constructor(val session: Session) {
    companion object {

        private const val CONNECTION_TIMEOUT = 30L

//        var BASE_URL = "https://gateway.marvel.com/"
//        var API_VERSION = "v1/"


        private val logger: HttpLoggingInterceptor
            get() {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                }.level = HttpLoggingInterceptor.Level.BODY
                return loggingInterceptor
            }


        fun <T> createService(
            serviceClass: Class<T>,
            isAuth: Boolean = false
        ): T {

            val okHttpClientBuilder = OkHttpClient().newBuilder()
            okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
//            if (isAuth) {
//                okHttpClientBuilder.addInterceptor(AuthInterceptor())
//                okHttpClientBuilder.authenticator(TokenAuthenticator(nonAuthService))
//            } else {
            okHttpClientBuilder.addInterceptor(NoneAuthInterceptor())
//            }

            okHttpClientBuilder.addInterceptor(logger)

            val okHttpClient = okHttpClientBuilder.build()

            var retrofit =
                Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)//+ BuildConfig.API_VERSION
                    .addConverterFactory(GsonConverterFactory.create(Gson())).client(okHttpClient)
                    .build()

            return retrofit.create(serviceClass)
        }

    }
}
