package com.radwaelsahn.currencyapp.di

import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataRepository
import com.radwaelsahn.currencyapp.data.datasources.remote.repositories.currencies.CurrenciesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideCurrenciesDataRepository(dataRepository: CurrenciesDataRepository): CurrenciesDataSource

}