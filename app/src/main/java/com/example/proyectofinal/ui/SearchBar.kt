package com.example.proyectofinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(it)
        },
        label = { Text("Buscar notas o tareas") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}