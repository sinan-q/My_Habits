package com.sinxn.myhabits.presentaion.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sinxn.myhabits.presentaion.main.components.MainBottomBar
import com.sinxn.myhabits.presentaion.main.components.NavigationGraph
import com.sinxn.myhabits.presentaion.util.BottomNavItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    startUpScreen: String,
    mainNavController: NavHostController
) {


    val navController = rememberNavController()
    val bottomNavItems =
        listOf(BottomNavItem.Home, BottomNavItem.Dashboard, BottomNavItem.Settings)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            MainBottomBar(navController = navController, items = bottomNavItems)
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            NavigationGraph(
                navController = navController,
                mainNavController = mainNavController,
                startUpScreen = startUpScreen,
            )
        }

    }
}