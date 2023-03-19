package com.sinxn.myhabits.di

import android.content.Context
import com.sinxn.myhabits.app.dataStore
import com.sinxn.myhabits.data.repository.SettingsRepositoryImpl
import com.sinxn.myhabits.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository = SettingsRepositoryImpl(context.dataStore)

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context
}
