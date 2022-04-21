package com.radwaelsahn.currencyapp.di


import android.app.Application
import android.content.Context
import com.radwaelsahn.currencyapp.data.datasources.local.db.MarvelDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun provideMarvelDatabase(db: MarvelDatabase): MarvelDatabase

}