package com.sinxn.myhabits.presentaion.util

sealed class Screen(val route: String, val name: String) {
    object Main : Screen("main_screen", "Main Screen")
    object HomeScreen : Screen("home_screen", "Home")
    object HabitAddScreen : Screen("habit_add_screen", "Add Habit")
    object DashboardScreen : Screen("dashboard_screen", "Dashboard")
    object SettingsScreen : Screen("settings_screen", "Settings")

}