package com.sinxn.myhabits.presentaion.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.domain.model.TaskAndProgress
import com.sinxn.myhabits.presentaion.util.Screen
import com.sinxn.myhabits.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,

    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.dashboardUiState
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
                        text = stringResource(id = R.string.dashboard),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = (Constants.collapsedTextSize + (Constants.expandedTextSize - Constants.collapsedTextSize) * (1 - scrollBehavior.state.collapsedFraction)).sp
                    )
                },
            )
        },
    ) {
        LaunchedEffect(true) { viewModel.onEvent(DashboardEvent.InitAll) }
        Box(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {

            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                    LazyRow {
                        item {
                            RowElementText(text = "Date")

                        }
                        itemsIndexed(items = uiState.tasks) { index: Int, item: TaskAndProgress ->
                            RowElementText(text = item.task.title)
                        }
                    }
                }
                items(items = (uiState.date..uiState.today).toList()) { date ->
                    LazyRow {
                        item {
                            RowElementText(
                                text = LocalDate.ofEpochDay(date).format(
                                    DateTimeFormatter.ofPattern("dd ,MMM")
                                )
                            )
                        }
                        itemsIndexed(items = uiState.tasks) { index: Int, taskAndProgress: TaskAndProgress ->
                            val item by remember(taskAndProgress) {
                                mutableStateOf(taskAndProgress.progress.find { progress -> progress.date == date })
                            }
                            val subTasksSize by remember { mutableStateOf(taskAndProgress.task.subTasks.size) }
                            val progress by remember(item) {
                                mutableStateOf(
                                    item?.subTasks?.filter { subTask -> subTask.isCompleted }?.size
                                        ?: 0
                                )
                            }
                            val progressText =
                                if (subTasksSize != 0) "$progress/$subTasksSize" else if (item?.isCompleted == true) "X" else "-"
                            RowElementText(progressText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowElementText(
    text: String
) {
    Column(
        modifier = Modifier
            .height(50.dp)
            .width(80.dp)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}