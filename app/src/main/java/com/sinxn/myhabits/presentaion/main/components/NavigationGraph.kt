package com.sinxn.myhabits.presentaion.main.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sinxn.myhabits.presentaion.main.DashboardScreen
import com.sinxn.myhabits.presentaion.main.HomeScreen
import com.sinxn.myhabits.presentaion.settings.SettingsScreen
import com.sinxn.myhabits.presentaion.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    startUpScreen: String,
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    NavHost(navController = navController, startDestination = startUpScreen) {

        composable(Screen.HomeScreen.route) {
            HomeScreen(mainNavController, scrollBehavior)
        }
        composable(Screen.DashboardScreen.route) {
            DashboardScreen(mainNavController, scrollBehavior)
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(mainNavController, scrollBehavior)
        }
    }
}