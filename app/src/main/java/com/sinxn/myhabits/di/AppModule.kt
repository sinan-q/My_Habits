package com.sinxn.myhabits.di

import android.content.Context
import androidx.room.Room
import com.sinxn.myhabits.data.repository.TaskRepositoryImpl
import com.sinxn.myhabits.app.dataStore
import com.sinxn.myhabits.data.local.MyHabitDatabase
import com.sinxn.myhabits.data.local.dao.AlarmDao
import com.sinxn.myhabits.data.local.dao.TaskDao
import com.sinxn.myhabits.data.repository.AlarmRepositoryImpl
import com.sinxn.myhabits.data.repository.SettingsRepositoryImpl
import com.sinxn.myhabits.domain.repository.AlarmRepository
import com.sinxn.myhabits.domain.repository.SettingsRepository
import com.sinxn.myhabits.domain.repository.TaskRepository
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
    fun provideMyBrainDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MyHabitDatabase::class.java,
        MyHabitDatabase.DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideTaskDao(myHabitDatabase: MyHabitDatabase) = myHabitDatabase.taskDao()

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun provideAlarmDao(myHabitDatabase: MyHabitDatabase) = myHabitDatabase.alarmDao()

    @Singleton
    @Provides
    fun provideAlarmRepository(alarmDao: AlarmDao): AlarmRepository = AlarmRepositoryImpl(alarmDao)


    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository = SettingsRepositoryImpl(context.dataStore)

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context
}
