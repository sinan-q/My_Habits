package com.sinxn.myhabits.presentaion.util

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object HomeScreen : Screen("home_screen")

    object DashboardScreen : Screen("dashboard_screen")
    object SettingsScreen : Screen("settings_screen")

}