package com.sinxn.myhabits.presentaion.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sinxn.myhabits.presentaion.main.components.TextInputDialog
import com.sinxn.myhabits.util.Constants

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 15.dp)
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