package com.sinxn.myhabits.presentaion.main.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sinxn.myhabits.presentaion.util.BottomNavItem

@Composable
fun MainBottomBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
) {
    BottomNavigation (backgroundColor = MaterialTheme.colorScheme.background) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach {
            BottomNavigationItem(
                icon = { Icon(
                    if (currentDestination?.route == it.route)
                        painterResource(it.iconSelected)
                    else
                        painterResource(it.icon),
                    tint = LocalContentColor.current,
                    contentDescription = stringResource(it.title),
                ) },
                selected = currentDestination?.route == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}