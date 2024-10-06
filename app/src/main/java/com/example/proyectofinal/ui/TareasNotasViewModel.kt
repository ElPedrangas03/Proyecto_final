package com.example.proyectofinal.ui

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TareasNotasViewModel : ViewModel() {
    var uiState by mutableStateOf(TareasNotasUiState())
        private set

    init {
        // Inicializar la lista con algunas tareas y notas predeterminadas
        uiState = uiState.copy(
            items = listOf(
                Item.Tarea("Terminar el diseño", "06-10-2024", "Detalles del diseño"),
                Item.Tarea("Jugar Fortnite", "08-10-2024", "Jugar con amigos"),
                Item.Nota("Ideas para el proyecto", "Explorar nuevas funcionalidades para la app")
            )
        )
    }

    fun completarTarea(index: Int) {
        val updatedItems = uiState.items.toMutableList()
        if (updatedItems[index] is Item.Tarea) {
            val tarea = updatedItems[index] as Item.Tarea
            updatedItems[index] = tarea.copy(completada = true)
            uiState = uiState.copy(items = updatedItems)
        }
    }

    fun eliminarItem(index: Int) {
        val updatedItems = uiState.items.toMutableList()
        updatedItems.removeAt(index)
        uiState = uiState.copy(items = updatedItems)
    }

    fun agregarTarea(titulo: String, fecha: String, descripcion: String) {
        val updatedItems = uiState.items.toMutableList()
        updatedItems.add(Item.Tarea(titulo, fecha, descripcion))
        uiState = uiState.copy(items = updatedItems)
    }

    fun agregarNota(titulo: String, contenido: String) {
        val updatedItems = uiState.items.toMutableList()
        updatedItems.add(Item.Nota(titulo, contenido))
        uiState = uiState.copy(items = updatedItems)
    }

    fun buscarItems(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }
}