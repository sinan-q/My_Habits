package com.sinxn.myhabits.presentaion.habitAdd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.domain.model.SubTask
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.presentaion.main.MainViewModel
import com.sinxn.myhabits.presentaion.main.TaskEvent
import com.sinxn.myhabits.util.settings.Interval
import com.sinxn.myhabits.util.settings.toInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitAddScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    var category by rememberSaveable { mutableStateOf(true) }
    var name by rememberSaveable { mutableStateOf("") }
    var emoji by rememberSaveable { mutableStateOf("\uD83D\uDE42") }
    var interval by rememberSaveable { mutableStateOf(Interval.DAILY) }
    var enableRemainder by rememberSaveable { mutableStateOf(false) }
    val subTasks = remember { mutableStateListOf<SubTask>() }
    val dueDate by remember { mutableStateOf(false) }




    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dashboard),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
            )
        }
    ) { padding ->
        // LaunchedEffect(true) { viewModel.onDashboardEvent(DashboardEvent.InitAll) }
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(start = 15.dp, end = 15.dp)
        )
        {
            Text(
                text = "Enter Habit Name",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.padding(top = 15.dp)) {
                Button(
                    modifier = Modifier.padding(start = 15.dp),
                    onClick = { category = true },
                    colors = ButtonDefaults.buttonColors(containerColor = if (category) MaterialTheme.colorScheme.primary else Color.Unspecified)
                ) {
                    Text(modifier = Modifier.padding(15.dp), text = "Do")
                }

                Button(
                    modifier = Modifier.padding(start = 15.dp),

                    onClick = { category = false },
                    colors = ButtonDefaults.buttonColors(containerColor = if (!category) MaterialTheme.colorScheme.primary else Color.Unspecified)
                ) {
                    Text(modifier = Modifier.padding(15.dp), text = "Don't")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Emoji",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                OutlinedTextField(
                    value = emoji,
                    onValueChange = { emoji = it },
                    modifier = Modifier.width(60.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    singleLine = true
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Interval",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                IntervalMenu { interval = it }
            }

            Text(
                text = "Subtasks",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Column {
                subTasks.forEachIndexed { index, item ->
                    SubTaskItem(
                        subTask = item,
                        onChange = { subTasks[index] = it },
                        onDelete = { subTasks.removeAt(index) }
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { subTasks.add(SubTask(title = "New Subtask")) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = " + Add subtask",
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Reminder",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Checkbox(checked = enableRemainder, onCheckedChange = { enableRemainder = it })


            }

        }
        Row(
            modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Cancel")
            }
            Button(
                onClick = {
                    viewModel.onEvent(
                        TaskEvent.AddTask(
                        Task(
                            title = name,
                            category = category,
                            interval = interval.toInt(),
                            emoji = emoji,
                            createdDate = System.currentTimeMillis(),
                            updatedDate = System.currentTimeMillis(),
                            subTasks = subTasks.toList(),
                            remainder = enableRemainder
                        )
                    ))
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Save")
            }
        }
    }
}


@Composable
fun SubTaskItem(
    subTask: SubTask,
    onChange: (SubTask) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.delete_sub_task),
            modifier = Modifier.clickable { onDelete() }
        )

        Spacer(Modifier.width(8.dp))
        BasicTextField(
            value = subTask.title,
            onValueChange = {
                onChange(subTask.copy(title = it))
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun IntervalMenu(
    onChange: (Interval) -> Unit
) {

    val intervals = listOf(Interval.DAILY, Interval.WEEKLY, Interval.MONTHLY)

    var expanded by remember {
        mutableStateOf(false)
    }
    var intervalSelected by remember {
        mutableStateOf(Interval.DAILY.title)
    }

    Box {
        Button(onClick = { expanded = true }) {
            Text(text = stringResource(id = intervalSelected))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            for (interval in intervals)
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = interval.title)) },
                    onClick = {
                        onChange(interval)
                        intervalSelected = interval.title
                    })
        }

    }
}