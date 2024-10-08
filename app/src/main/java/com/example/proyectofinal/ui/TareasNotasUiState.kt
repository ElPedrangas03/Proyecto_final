package com.example.proyectofinal.ui

import androidx.compose.runtime.Immutable

data class TareasNotasUiState(
    val items: List<Item> = emptyList(),
    val searchQuery: String = ""
)