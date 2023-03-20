package com.sinxn.myhabits.domain.repository

import com.sinxn.myhabits.domain.model.Alarm

interface AlarmRepository {

    suspend fun getAlarms(): List<Alarm>

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)

    suspend fun deleteAlarm(id: Int)
}