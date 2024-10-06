package com.example.proyectofinal.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterButton() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Título", "Fecha de creación", "Fecha de vencimiento")
    var selectedFilter by remember { mutableStateOf(items[0]) }

    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { expanded = true }) {
            Text("Filtrar por: $selectedFilter")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(filter) },
                    onClick = {
                        selectedFilter = filter
                        expanded = false
                    }
                )
            }
        }
    }
}