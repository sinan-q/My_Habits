package com.sinxn.myhabits.data.repository

import com.sinxn.myhabits.data.local.dao.AlarmDao
import com.sinxn.myhabits.domain.model.Alarm
import com.sinxn.myhabits.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlarmRepository {

    override suspend fun getAlarms(): List<Alarm> {
        return withContext(ioDispatcher) {
            alarmDao.getAll()
        }
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        withContext(ioDispatcher) {
            alarmDao.insert(alarm)
        }
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        withContext(ioDispatcher) {
            alarmDao.delete(alarm)
        }
    }

    override suspend fun deleteAlarm(id: Int) {
        withContext(ioDispatcher) {
            alarmDao.delete(id)
        }
    }
}