package com.sinxn.myhabits.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mhss.app.mybrain.domain.model.*
import com.sinxn.myhabits.data.local.converters.DBConverters
import com.sinxn.myhabits.data.local.dao.AlarmDao
import com.sinxn.myhabits.data.local.dao.TaskDao
import com.sinxn.myhabits.domain.model.Alarm

@Database(
    entities = [Task::class, Alarm::class],
    version = 1
)

@TypeConverters(DBConverters::class)
abstract class MyHabitDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun alarmDao(): AlarmDao

    companion object {
        const val DATABASE_NAME = "my_habits_db"
    }
}