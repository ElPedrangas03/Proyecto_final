package com.example.proyectofinal.ui

data class TareasNotasUiState(
    val items: List<Item> = emptyList(),
    val searchQuery: String = ""
)