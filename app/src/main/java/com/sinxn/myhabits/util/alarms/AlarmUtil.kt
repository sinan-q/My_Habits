package com.sinxn.myhabits.util.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.sinxn.myhabits.domain.model.Alarm
import com.sinxn.myhabits.util.Constants

fun AlarmManager.scheduleAlarm(alarm: Alarm, context: Context) {

    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra(Constants.TASK_ID_EXTRA, alarm.id)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    //AlarmManagerCompat.setExactAndAllowWhileIdle(this, AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent)
}

fun AlarmManager.cancelAlarm(alarmId: Int, context: Context) {

    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarmId,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    cancel(pendingIntent)
}