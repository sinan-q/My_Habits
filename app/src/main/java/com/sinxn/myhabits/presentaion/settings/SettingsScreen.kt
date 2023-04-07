package com.sinxn.myhabits.presentaion.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sinxn.myhabits.R
import com.sinxn.myhabits.presentaion.main.components.TextInputDialog
import com.sinxn.myhabits.presentaion.util.Screen
import com.sinxn.myhabits.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

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
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = (Constants.collapsedTextSize + (Constants.expandedTextSize - Constants.collapsedTextSize) * (1 - scrollBehavior.state.collapsedFraction)).sp
                    )
                },
            )
        },
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it)
            .padding(top = 15.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .verticalScroll(rememberScrollState())
            .clickable { showDialog = true }) {

            Text(text = "User Name", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = viewModel.getSettings(stringPreferencesKey(Constants.USER_NAME), "User")
                    .collectAsState(
                        initial = "User"
                    ).value, style = MaterialTheme.typography.labelSmall
            )

            if (showDialog) TextInputDialog(
                title = "User Name",
                onDismiss = { showDialog = false },
                onResult = {
                    viewModel.saveSettings(
                        stringPreferencesKey(Constants.USER_NAME), it
                    )
                })
        }
    }


}