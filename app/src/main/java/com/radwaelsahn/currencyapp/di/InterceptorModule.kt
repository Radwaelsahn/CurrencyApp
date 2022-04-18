package com.radwaelsahn.currencyapp.di

import com.radwaelsahn.currencyapp.data.source.remote.networking.AuthInterceptor
import com.radwaelsahn.currencyapp.data.source.remote.networking.TokenAuthenticator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuthInterceptorQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class TokenAuthenticatorQualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class InterceptorModule {

    @AuthInterceptorQualifier
    @Binds
    @Singleton
    abstract fun bindsAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor

    @TokenAuthenticatorQualifier
    @Binds
    @Singleton
    abstract fun provideTokenAuthenticator(authenticator: TokenAuthenticator): Authenticator

}