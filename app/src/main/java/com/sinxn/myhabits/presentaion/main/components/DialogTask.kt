package com.sinxn.myhabits.presentaion.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sinxn.myhabits.presentaion.main.MainViewModel

@Composable
fun TaskDialog(
    task: MainViewModel.TaskUiState,
    dialogDismiss: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
) {
    var id by rememberSaveable { mutableStateOf(0L) }
    var habitId by rememberSaveable { mutableStateOf(0L) }

    LaunchedEffect(task) {
        id = task.progress.habitId
        habitId = task.progress.id
    }

    Dialog(
        onDismissRequest = {
            dialogDismiss()
        }
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
                Button(onClick = { onComplete() }) {
                    Text(text = "Mark as Completed")
                }
                Button(onClick = { onDelete() }) {
                    Text(text = "Delete Entirely", color = Color.Red)
                }
            }
        }
    }
}