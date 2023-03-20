package com.sinxn.myhabits.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhss.app.mybrain.domain.model.SubTask

class DBConverters {

    @TypeConverter
    fun fromSubTasksList(value: List<SubTask>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubTasksList(value: String): List<SubTask> {
        val gson = Gson()
        val type = object : TypeToken<List<SubTask>>() {}.type
        return gson.fromJson(value, type)
    }

}