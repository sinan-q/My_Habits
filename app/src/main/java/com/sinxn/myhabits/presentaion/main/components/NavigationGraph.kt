package com.sinxn.myhabits.presentaion.main.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sinxn.myhabits.presentaion.main.DashboardScreen
import com.sinxn.myhabits.presentaion.main.HomeScreen
import com.sinxn.myhabits.presentaion.settings.SettingsScreen
import com.sinxn.myhabits.presentaion.util.Screen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    startUpScreen: String,
) {
    NavHost(navController = navController, startDestination = startUpScreen){

        composable(Screen.HomeScreen.route){
            HomeScreen(mainNavController)
        }
        composable(Screen.DashboardScreen.route){
            DashboardScreen(mainNavController)
        }

        composable(Screen.SettingsScreen.route){
            SettingsScreen(mainNavController)
        }
    }
}