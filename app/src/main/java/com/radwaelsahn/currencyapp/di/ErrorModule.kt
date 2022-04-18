package com.radwaelsahn.currencyapp.di

import com.radwaelsahn.currencyapp.data.error.mapper.ErrorMapper
import com.radwaelsahn.currencyapp.data.error.mapper.ErrorMapperInterface

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.logging.ErrorManager
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ErrorModule {
//    @Binds
//    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorFactory

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperInterface
}
