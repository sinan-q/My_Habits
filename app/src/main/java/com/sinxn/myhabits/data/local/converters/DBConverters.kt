package com.sinxn.myhabits.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sinxn.myhabits.domain.model.SubTask
import com.sinxn.myhabits.domain.model.SubTaskProgress

class DBConverters {

    @TypeConverter
    fun fromSubTasksList(value: List<SubTask>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubTasksList(value: String?): List<SubTask> {
        if (value==null) return emptyList()
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSubTaskProgressList(value: List<SubTaskProgress>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SubTaskProgress>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubTaskProgressList(value: String?): List<SubTaskProgress?> {
        if (value==null) return emptyList()

        val gson = Gson()
        val type = object : TypeToken<List<SubTaskProgress>>() {}.type
        return gson.fromJson(value, type)
    }

}