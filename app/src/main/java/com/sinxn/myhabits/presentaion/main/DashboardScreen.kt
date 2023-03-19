package com.sinxn.myhabits.presentaion.main

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.presentaion.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.dashboard),
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
            },
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp
        )
    }
    ) {
       // LaunchedEffect(true) { viewModel.onDashboardEvent(DashboardEvent.InitAll) }
        //TODO DashboardScreen
    }
}