package com.sinxn.myhabits.presentaion.habitAdd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.domain.model.SubTask
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.presentaion.main.MainViewModel
import com.sinxn.myhabits.presentaion.main.TaskEvent
import com.sinxn.myhabits.util.Constants.collapsedTextSize
import com.sinxn.myhabits.util.Constants.expandedTextSize
import com.sinxn.myhabits.util.settings.Interval
import com.sinxn.myhabits.util.settings.toInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitAddScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var mDisplayMenu by remember { mutableStateOf(false) }


    var category by rememberSaveable { mutableStateOf(true) }
    var name by rememberSaveable { mutableStateOf("") }
    var emoji by rememberSaveable { mutableStateOf("\uD83D\uDE42") }
    var interval by rememberSaveable { mutableStateOf(Interval.DAILY) }
    var enableRemainder by rememberSaveable { mutableStateOf(false) }
    val subTasks = remember { mutableStateListOf<SubTask>() }
    // val dueDate by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.create_habit),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = (collapsedTextSize + (expandedTextSize - collapsedTextSize) * (1 - scrollBehavior.state.collapsedFraction)).sp
                    )
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 15.dp)
        )
        {
            Text(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .padding(bottom = 15.dp),
                text = "Enter Habit Name",
                style = MaterialTheme.typography.headlineLarge
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.padding(top = 30.dp)) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Category",
                    style = MaterialTheme.typography.headlineLarge
                )
                Button(
                    modifier = Modifier.padding(start = 15.dp),
                    onClick = { category = true },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = if (category) MaterialTheme.colorScheme.primary else Color.Unspecified)
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp), text = "Do",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = if (category) Color.Unspecified else MaterialTheme.colorScheme.onBackground
                    )
                }

                Button(
                    modifier = Modifier.padding(start = 15.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = { category = false },
                    colors = ButtonDefaults.buttonColors(containerColor = if (!category) MaterialTheme.colorScheme.primary else Color.Unspecified)
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp), text = "Don't",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = if (!category) Color.Unspecified else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "Select Emoji",
                    style = MaterialTheme.typography.headlineLarge
                )
                OutlinedTextField(
                    value = emoji,
                    onValueChange = {
                        if (it.length < 3)
                            emoji = it
                    },
                    modifier = Modifier.width(60.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    singleLine = true
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Interval",
                    style = MaterialTheme.typography.headlineLarge
                )
                IntervalMenu { interval = it }
            }

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "Subtasks",
                style = MaterialTheme.typography.headlineLarge
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
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = "Enable Reminder",
                    style = MaterialTheme.typography.headlineLarge
                )
                Checkbox(checked = enableRemainder, onCheckedChange = { enableRemainder = it })


            }

        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp),
                onClick = {
                    navController.popBackStack()
                },
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = "Cancel", style = MaterialTheme.typography.headlineLarge)
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),

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
                        )
                    )
                    navController.popBackStack()
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Save", style = MaterialTheme.typography.headlineLarge)
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
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp),
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
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
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
        Button(
            onClick = { expanded = true },
            shape = MaterialTheme.shapes.medium,
        ) {
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