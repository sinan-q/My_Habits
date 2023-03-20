package com.sinxn.myhabits.util.alarms

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sinxn.myhabits.domain.use_case.alarm.DeleteAlarmUseCase
import com.sinxn.myhabits.domain.use_case.task.GetTaskByIdUseCase
import com.sinxn.myhabits.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deleteAlarmUseCase: DeleteAlarmUseCase
    @Inject
    lateinit var getTaskByIdUseCase: GetTaskByIdUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        runBlocking {
            val task = intent?.getIntExtra(Constants.TASK_ID_EXTRA, 0)?.let { getTaskByIdUseCase(LocalDate.now().toEpochDay(),it.toLong()) }
            task?.let {
                val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                //manager.sendNotification(task, context, task.id)
                deleteAlarmUseCase(task.task.id)
            }
        }
    }
}