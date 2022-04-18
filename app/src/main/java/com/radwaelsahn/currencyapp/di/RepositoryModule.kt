package com.radwaelsahn.currencyapp.di

import com.radwaelsahn.currencyapp.data.source.remote.repositories.characters.CharactersDataRepository
import com.radwaelsahn.currencyapp.data.source.remote.repositories.characters.CharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideCharactersDataRepository(dataRepository: CharactersDataRepository): CharactersDataSource

}