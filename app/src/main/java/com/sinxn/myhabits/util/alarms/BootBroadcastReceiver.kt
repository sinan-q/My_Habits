package com.sinxn.myhabits.util.alarms

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sinxn.myhabits.domain.use_case.alarm.GetAllAlarmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class BootBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getAllAlarms: GetAllAlarmsUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            runBlocking {
                val alarms = getAllAlarms()
                alarms.forEach {
                    alarmManager.scheduleAlarm(it, context)
                }
            }
        }

    }

}