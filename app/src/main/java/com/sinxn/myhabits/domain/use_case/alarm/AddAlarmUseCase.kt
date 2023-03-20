package com.sinxn.myhabits.domain.use_case.alarm

import android.app.AlarmManager
import android.content.Context
import com.sinxn.myhabits.util.alarms.scheduleAlarm
import com.sinxn.myhabits.domain.model.Alarm
import com.sinxn.myhabits.domain.repository.AlarmRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val context: Context
) {
    suspend operator fun invoke(alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.scheduleAlarm(alarm, context)
        alarmRepository.insertAlarm(alarm)
    }
}