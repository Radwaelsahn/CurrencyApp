/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.radwaelsahn.currencyapp.di

import android.app.Application
import android.content.Context
import com.radwaelsahn.currencyapp.App
import com.radwaelsahn.currencyapp.data.datasources.local.LocalRepository
import com.radwaelsahn.currencyapp.data.datasources.local.LocalSource
import com.radwaelsahn.currencyapp.data.datasources.local.Session
import com.radwaelsahn.currencyapp.data.datasources.local.SharedPrefHelper
import com.radwaelsahn.currencyapp.data.datasources.local.mapper.CharacterLocalMapper
import com.radwaelsahn.currencyapp.data.datasources.remote.networking.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(application: Application): App = application as App

    @Singleton
    @Provides
    fun provideSharedPrefHelper(@ApplicationContext context: Context): SharedPrefHelper = SharedPrefHelper(context)

    @Singleton
    @Provides
    fun provideSession(sharedPrefHelper: SharedPrefHelper): Session = Session(sharedPrefHelper)

    @Singleton
    @Provides
    fun provideServiceGenerator(session: Session): ServiceGenerator = ServiceGenerator(session) // TODO Remove after refactor

    @Provides
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    fun provideLocalSource(
        context: Context,
        mapper: CharacterLocalMapper
    ): LocalSource {
        return LocalRepository(context,mapper)
    }

}
