package com.sinxn.myhabits.presentaion.main.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.SubTask
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.util.settings.Interval
import com.sinxn.myhabits.util.settings.toInterval
import java.time.LocalDate


@Composable
fun CompleteDialogContent(
    task: TaskWithProgress,
    dialogDismiss: (Progress) -> Unit,
) {
    var id by rememberSaveable { mutableStateOf(0L) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var priority by rememberSaveable { mutableStateOf(Interval.DAILY) }
    var dueDate by rememberSaveable { mutableStateOf(0L) }
    var dueDateExists by rememberSaveable { mutableStateOf(false) }
    var completed by rememberSaveable { mutableStateOf(false) }
    val subTasks = remember { mutableStateListOf<SubTask>() }

    LaunchedEffect(task) {
        title = task.task.title
        description = task.task.emoji
        priority = task.task.interval.toInterval()
        dueDateExists = task.task.remainder
        id = task.task.id
        subTasks.clear()
        if (task.progress == null) {
            completed = false
            subTasks.addAll(task.task.subTasks.toMutableList())
        }
        else{
            Log.d("TAG", "1: ${task.progress.subTasks.size}")

            completed = task.progress.isCompleted
            subTasks.addAll(task.progress.subTasks)
        }
    }
    Dialog(
        onDismissRequest = {
            Log.d("TAG", "2: ${subTasks.size}")
            dialogDismiss(Progress(habitId = id,date = LocalDate.now().toEpochDay() , subTasks = subTasks.toList()))
                           },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Card(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                subTasks.forEachIndexed { index, item ->
                    SubTaskItemProgress(
                        subTask = item,
                        onChange = {
                            Log.d("TAG", "3: ${subTasks.size}")
                            subTasks[index] = subTasks[index].copy(isCompleted = it) },
                    )
                }
            }
        }
    }

}

@Composable
fun SubTaskItemProgress(
    subTask: SubTask,
    onChange: (Boolean) -> Unit,
) {
    var checked by remember {
        mutableStateOf(subTask.isCompleted)
    }
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it
                onChange(it) },
        )
        Spacer(Modifier.width(8.dp))

        Text(
            text = subTask.title,
            modifier = Modifier.fillMaxWidth()
        )

    }
}
