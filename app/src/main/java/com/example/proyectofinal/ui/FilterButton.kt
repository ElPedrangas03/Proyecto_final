package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterButton(tabIndex: Int, filtroSeleccionado: String?, onFilterSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = when (tabIndex) {
        0 -> listOf("Fecha de vencimiento", "Fecha de creación", "Título")
        else -> listOf("Fecha de creación", "Título")
    }
    var selectedFilter by remember { mutableStateOf(items[0]) }

    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { expanded = true }) {
            Text("Filtrar por: ${filtroSeleccionado ?: items[0]}")
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
                        onFilterSelected(filter)
                    }
                )
            }
        }
    }
}
