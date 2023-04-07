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
import com.sinxn.myhabits.presentaion.main.MainViewModel
import java.time.LocalDate


@Composable
fun SubTaskDialog(
    task: MainViewModel.TaskUiState,
    date: Long = LocalDate.now().toEpochDay(),
    dialogDismiss: (Progress) -> Unit,
) {
    var id by rememberSaveable { mutableStateOf(0L) }

    var completed by rememberSaveable { mutableStateOf(false) }
    val subTasks = remember { mutableStateListOf<SubTask>() }
    var streak by remember { mutableStateOf(0) }


    LaunchedEffect(task) {
        id = task.progress.habitId
        subTasks.clear()
        completed = task.progress.isCompleted
        subTasks.addAll(task.progress.subTasks)
        streak = task.progress.streak
    }
    Dialog(
        onDismissRequest = {
            val doneAlready = completed
            if (subTasks.size == subTasks.filter { it.isCompleted }.size) completed = true

            dialogDismiss(
                Progress(
                    habitId = id,
                    date = date,
                    isCompleted = completed,
                    subTasks = subTasks.toList(),
                    streak = when {
                        doneAlready && !completed -> kotlin.math.max(streak - 1, 0)
                        !doneAlready && completed -> streak + 1
                        else -> streak
                    }
                )
            )
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
                            subTasks[index] = subTasks[index].copy(isCompleted = it)
                        },
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
            onCheckedChange = {
                checked = it
                onChange(it)
            },
        )
        Spacer(Modifier.width(8.dp))

        Text(
            text = subTask.title,
            modifier = Modifier.fillMaxWidth()
        )

    }
}
