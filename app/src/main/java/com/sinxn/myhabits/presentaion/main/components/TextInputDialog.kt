package com.sinxn.myhabits.presentaion.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TextInputDialog(
    title: String,
    initialValue: String = "",
    onDismiss: () -> Unit,
    onResult: (String) -> Unit

) {
    var value by remember {
        mutableStateOf(initialValue)
    }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                TextField(value = value, onValueChange = { value = it })

                Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = { onDismiss() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        onDismiss()
                        onResult(value)
                    }) {
                        Text(text = "Submit")
                    }
                }

            }
        }
    }
}