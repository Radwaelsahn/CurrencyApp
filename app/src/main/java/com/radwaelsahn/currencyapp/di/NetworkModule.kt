package com.radwaelsahn.currencyapp.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.radwaelsahn.currencyapp.BuildConfig
import com.radwaelsahn.currencyapp.data.source.remote.repositories.characters.CharactersService
import com.radwaelsahn.currencyapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    @Singleton
    @Provides
    fun OkHttpClient(
        @AuthInterceptorQualifier authInterceptor: Interceptor,
        @TokenAuthenticatorQualifier tokenAuthenticator: Authenticator
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = //if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
//        else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient().newBuilder().apply {
            connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(authInterceptor)
            authenticator(tokenAuthenticator)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.API_VERSION)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideCharactersService(retrofitBuilder: Retrofit.Builder): CharactersService = retrofitBuilder.build().create(
        CharactersService::class.java)



}