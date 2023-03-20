package com.sinxn.myhabits.presentaion.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mhss.app.mybrain.domain.model.Task
import com.sinxn.myhabits.R
import com.sinxn.myhabits.presentaion.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
){
    val uiState = viewModel.tasksUiState

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.padding(bottom = 55.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home),
                    )
                },
            )
        },

        floatingActionButton =
        {
            FloatingActionButton(onClick = { navController.navigate(Screen.HabitAddScreen.route) }){
                Row(Modifier.padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = android.R.drawable.ic_input_add), contentDescription = null)
                    Text(text = "Add Habit")
                }
            }
        }

    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(start = 15.dp, end = 15.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Welcome,", //TODO get from res
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Name", //TODO get name
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "25,July 2023", //TODO selected date
                    fontSize = 16.sp
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(android.R.drawable.ic_menu_my_calendar),
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.size(24.dp))
                }

            }
            LazyRow(state = lazyListState,horizontalArrangement = Arrangement.Center) {
                items(viewModel.dateRow) { item ->
                    DateRow(item.Date,item.dateString,viewModel.currentDate.toString()) { }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "Do's",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "See More",
                    fontSize = 16.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.outline
                )
            }
            LazyRow(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                items(uiState.goodTasks, key = { it.id }) {task ->
                    HabitRow(task) }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Dont's",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = android.R.drawable.presence_invisible), contentDescription = null,modifier = Modifier.size(24.dp))
                    }
                }

                Text(
                    text = "See More",
                    fontSize = 16.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.outline
                )
            }
            LazyRow() {
                items(uiState.badTasks, key = { it.id }) { task ->
                    HabitRow(task)
                }
            }

        }
    }

    LaunchedEffect(lazyListState) {
        lazyListState.animateScrollToItem(lazyListState.layoutInfo.visibleItemsInfo.lastIndex / 2)
    }
}


@Composable
fun DateRow(
    dateString: String,
    date: String,
    today: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .size(80.dp)
        .padding(end = 15.dp)
        .clickable { onClick() }, contentAlignment = Alignment.Center ) {
        if (date == today) modifier
            .background(color = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
            .clip(RoundedCornerShape(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally  ) {
            Text(text = dateString, fontSize = 14.sp)
            Text(text = date, fontSize = 14.sp)
        }

    }
}

@Composable
fun HabitRow(
    task: Task
) {
    Box(modifier = Modifier
        .padding(end = 35.dp)
        .width(130.dp)
        .height(150.dp)
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(text = task.emoji, fontSize = 45.sp)
        }

        Column( modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = task.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = task.isCompleted.toString())
        }
    }
}

