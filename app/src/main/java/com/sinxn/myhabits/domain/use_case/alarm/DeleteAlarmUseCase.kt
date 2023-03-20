package com.sinxn.myhabits.domain.use_case.alarm

import android.app.AlarmManager
import android.content.Context
import com.sinxn.myhabits.util.alarms.cancelAlarm
import com.sinxn.myhabits.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val context: Context
) {
    suspend operator fun invoke(alarmId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancelAlarm(alarmId, context)
        alarmRepository.deleteAlarm(alarmId)
    }
}