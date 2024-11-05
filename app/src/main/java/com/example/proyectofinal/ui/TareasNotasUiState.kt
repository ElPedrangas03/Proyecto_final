package com.example.proyectofinal.ui

import androidx.compose.runtime.Immutable
import com.example.proyectofinal.data.Nota
import com.example.proyectofinal.data.Tarea

data class TareasNotasUiState(
    val tareas: List<Tarea> = emptyList(),
    val notas: List<Nota> = emptyList(),
    val searchQuery: String = ""
)