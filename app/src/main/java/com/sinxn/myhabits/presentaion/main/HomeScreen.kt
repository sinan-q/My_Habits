package com.sinxn.myhabits.presentaion.main

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.presentaion.main.components.SubTaskDialog
import com.sinxn.myhabits.presentaion.main.components.TaskDialog
import com.sinxn.myhabits.presentaion.settings.SettingsViewModel
import com.sinxn.myhabits.presentaion.util.Screen
import com.sinxn.myhabits.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.tasksUiState
    var openSubTaskDialog by rememberSaveable { mutableStateOf(false) }
    var openTaskDialog by rememberSaveable { mutableStateOf(false) }
    val today by rememberSaveable {
        mutableStateOf(LocalDate.now().toEpochDay())
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var mDisplayMenu by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = mDisplayMenu,
                        onDismissRequest = { mDisplayMenu = false }) {
                        DropdownMenuItem(
                            text = { Text(text = "Settings") },
                            onClick = {
                                navController.navigate(Screen.SettingsScreen.route)
                            })
                    }

                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = (Constants.collapsedTextSize + (Constants.expandedTextSize - Constants.collapsedTextSize) * (1 - scrollBehavior.state.collapsedFraction)).sp
                    )
                },
            )
        },
        floatingActionButton =
        {
            FloatingActionButton(onClick = { navController.navigate(Screen.HabitAddScreen.route) }) {
                Row(
                    Modifier.padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_input_add),
                        contentDescription = null
                    )
                    Text(
                        text = "Add Habit", style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())

        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Welcome,", //TODO get from res
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = settingsViewModel.getSettings(
                    stringPreferencesKey(Constants.USER_NAME),
                    "User"
                ).collectAsState(
                    initial = "User"
                ).value,
                style = MaterialTheme.typography.headlineLarge

            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "<",
                    fontSize = 16.sp
                )
                Text(
                    text = LocalDate.ofEpochDay(uiState.date)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE), //TODO selected date
                    fontSize = 16.sp
                )
                Text(
                    text = ">",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(horizontalArrangement = Arrangement.Center) {
                items(uiState.dateRow) { item ->

                    DateRow(item.Date, item.dateString, item.epoch, uiState.date, today,
                        onClick = {
                            viewModel.onEvent(TaskEvent.OnDateChange(item.epoch))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = LocalContentColor.current)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "Do's",
                    style = MaterialTheme.typography.headlineLarge

                )
                Text(
                    text = "See More",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            LazyRow(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                items(uiState.goodTasks) { task ->
                    HabitRow(taskWithProgress = { task },
                        onClick = {
                            viewModel.onEvent(TaskEvent.SetTask(task = task))
                            if (task.subTasks.isNotEmpty()) openSubTaskDialog = true
                            viewModel.onEvent(
                                TaskEvent.UpdateProgress(
                                    viewModel.taskDetailsUiState.progress.copy(
                                        isCompleted = !viewModel.taskDetailsUiState.progress.isCompleted
                                    )
                                )
                            )
                        },
                        onLongClick = {
                            viewModel.onEvent(TaskEvent.SetTask(task = task))
                            openTaskDialog = true
                        })
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Dont's",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_hide),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Text(
                    text = "See More",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            LazyRow {
                items(uiState.badTasks) { task ->
                    HabitRow(taskWithProgress = { task },
                        onClick = {
                            viewModel.onEvent(TaskEvent.SetTask(task = task))
                            if (task.subTasks.isNotEmpty()) openSubTaskDialog = true
                            else {
                                val completed = !viewModel.taskDetailsUiState.progress.isCompleted
                                viewModel.onEvent(
                                    TaskEvent.UpdateProgress(
                                        viewModel.taskDetailsUiState.progress.copy(
                                            isCompleted = completed,
                                        )
                                    )
                                )
                            }
                        },
                        onLongClick = {
                            viewModel.onEvent(TaskEvent.SetTask(task = task))
                            openTaskDialog = true
                        })

                }
            }
            if (openSubTaskDialog)
                SubTaskDialog(
                    task = viewModel.taskDetailsUiState,
                    date = uiState.date
                ) {
                    openSubTaskDialog = false
                    viewModel.onEvent(TaskEvent.UpdateProgress(it))
                }
            if (openTaskDialog)
                TaskDialog(
                    task = viewModel.taskDetailsUiState,
                    dialogDismiss = { openTaskDialog = false },
                    onDelete = { viewModel.onEvent(TaskEvent.DeleteTask(it)) },
                    onComplete = {}
                )

        }
    }

}

@Composable
fun DateRow(
    date: String,
    dateString: String,
    epoch: Long,
    selectedDate: Long = LocalDate.now().toEpochDay(),
    today: Long,
    onClick: () -> Unit
) {
    var clickable by remember {
        mutableStateOf(true)
    }
    var modifier = Modifier
        .padding(end = 15.dp)
        .height(50.dp)
        .width(60.dp)

    if (epoch == selectedDate) {
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)


    }
    if (epoch > today) clickable = false


    Box(
        modifier = modifier.clickable(enabled = clickable) { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = dateString, style = MaterialTheme.typography.labelSmall)
            Text(text = date, style = MaterialTheme.typography.labelMedium)
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitRow(
    taskWithProgress: () -> (TaskWithProgress),
    onClick: () -> Unit,
    onLongClick: () -> Unit

) {
    val subTasksSize by remember { mutableStateOf(taskWithProgress().subTasks.size) }

    val progress by remember(taskWithProgress()) {
        mutableStateOf(
            taskWithProgress().progress?.subTasks?.filter { it.isCompleted }?.size ?: 0
        )
    }
    val streak by remember(taskWithProgress()) {
        mutableStateOf(
            taskWithProgress().progress?.streak ?: 0
        )
    }

    val progressText =
        if (subTasksSize != 0) "$progress/$subTasksSize" else if (taskWithProgress().progress?.isCompleted == true) "Completed" else "Not Completed"

    Box(
        modifier = Modifier
            .padding(end = 5.dp)
            .width(120.dp)
            .height(150.dp)

            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(4.dp)
            )
            .combinedClickable(onClick = { onClick() },
                onLongClick = { onLongClick() })
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(text = taskWithProgress().emoji, fontSize = 45.sp)
        }

        Column( modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = taskWithProgress().title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = progressText, style = MaterialTheme.typography.labelSmall)
            if (streak > 1)
                Text(
                    text = "$streak day Streak",
                    style = MaterialTheme.typography.labelSmall
                )
            Spacer(Modifier.height(15.dp))
        }
    }
}

