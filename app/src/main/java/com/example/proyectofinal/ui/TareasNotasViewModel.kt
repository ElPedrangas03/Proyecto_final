package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.room.util.copy
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class TareasNotasViewModel : ViewModel() {
    var uiState by mutableStateOf(TareasNotasUiState())
        private set

    init {
        // Inicializar la lista con algunas tareas y notas predeterminadas
        uiState = uiState.copy(
            items = listOf(
                Item.Tarea("Terminar el diseño",
                    LocalDateTime.of(2024,10,10,19,0),
                    LocalDateTime.of(2024,10,6,10,25),
                    "Detalles del diseño"),
                Item.Tarea("Jugar Fortnite",
                    LocalDateTime.of(2024,10,8,18,0),
                    LocalDateTime.of(2024,10,6,9,25),
                    "Jugar con amigos"),
                Item.Nota("Ideas para el proyecto", LocalDateTime.of(2024,10,6,8,25),"Explorar nuevas funcionalidades para la app")
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

    fun agregarTarea(titulo: String, fecha: LocalDateTime, fechaCreacion:LocalDateTime, descripcion: String) {
        val updatedItems = uiState.items.toMutableList()
        updatedItems.add(Item.Tarea(titulo, fecha, fechaCreacion, descripcion))
        uiState = uiState.copy(items = updatedItems)
    }

    fun agregarNota(titulo: String, fechaCreacion:LocalDateTime, contenido: String) {
        val updatedItems = uiState.items.toMutableList()
        updatedItems.add(Item.Nota(titulo, fechaCreacion, contenido))
        uiState = uiState.copy(items = updatedItems)
    }

    fun editarTarea(titulo: String, fecha: LocalDateTime, fechaCreacion:LocalDateTime, descripcion: String, id: Int)
    {
        val updateItems = uiState.items.toMutableList()
        updateItems[id]=Item.Tarea(titulo, fecha, fechaCreacion, descripcion)
        uiState = uiState.copy(items = updateItems)
    }
    fun editarNota(titulo: String, fechaCreacion:LocalDateTime, contenido: String, id: Int)
    {
        val updatedItems = uiState.items.toMutableList()
        updatedItems[id]=Item.Nota(titulo, fechaCreacion, contenido)
        uiState = uiState.copy(items = updatedItems)
    }

    fun buscarItems(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }

    /*fun filtrarItems(filtro: String,tabIndex: Int) {
        val itemsFiltrados = when (tabIndex) {
            0 -> {
                when (filtro) {
                    "Título" -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.titulo }
                    "Fecha de creación" -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.fechaCreacion }
                    "Fecha de vencimiento" -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.fecha }
                    else -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.fecha }
                }
            }
            1 -> {
                when (filtro) {
                    "Título" -> uiState.items.filterIsInstance<Item.Nota>().sortedBy { it.titulo }
                    "Fecha de creación" -> uiState.items.filterIsInstance<Item.Nota>().sortedBy { it.fechaCreacion }
                    else -> uiState.items.filterIsInstance<Item.Nota>().sortedBy { it.fechaCreacion }
                }
            }
            else -> uiState.items
        }

        uiState = uiState.copy(items = itemsFiltrados)
    }*/
    /*fun obtenerItemsFiltrados(filtro: String, tabIndex: Int): List<Item> {
        return when (tabIndex) {
            0 -> {
                when (filtro) {
                    "Título" -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.titulo }
                    "Fecha de creación" -> uiState.items.filterIsInstance<Item.Tarea>().sortedByDescending { it.fechaCreacion }
                    "Fecha de vencimiento" -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.fecha }
                    else -> uiState.items.filterIsInstance<Item.Tarea>().sortedBy { it.fecha }
                }
            }
            1 -> { // Para Notas (tabIndex == 1)
                when (filtro) {
                    "Título" -> uiState.items.filterIsInstance<Item.Nota>().sortedBy { it.titulo }
                    "Fecha de creación" -> uiState.items.filterIsInstance<Item.Nota>().sortedByDescending { it.fechaCreacion }
                    else -> uiState.items.filterIsInstance<Item.Nota>().sortedBy { it.fechaCreacion }
                }
            }
            else -> uiState.items
        }
    }*/

    fun obtenerItemsFiltrados(filtro: String, tabIndex: Int, mostrarCompletadas: Boolean): List<Item> {
        return when (tabIndex) {
            0 -> {
                val tareasFiltradas = uiState.items.filterIsInstance<Item.Tarea>()
                val tareasVisibles = if (!mostrarCompletadas) {
                    tareasFiltradas.filter { !it.completada } // Excluir tareas completadas
                } else {
                    tareasFiltradas.filter { it.completada }// Incluir todas las tareas si se están mostrando completadas
                }

                when (filtro) {
                    "Título" -> tareasVisibles.sortedBy { it.titulo }
                    "Fecha de creación" -> tareasVisibles.sortedByDescending { it.fechaCreacion }
                    "Fecha de vencimiento" -> tareasVisibles.sortedBy { it.fecha }
                    else -> tareasVisibles.sortedBy { it.fecha }
                }
            }
            1 -> { // Para Notas (tabIndex == 1)
                val notasFiltradas = uiState.items.filterIsInstance<Item.Nota>()
                when (filtro) {
                    "Título" -> notasFiltradas.sortedBy { it.titulo }
                    "Fecha de creación" -> notasFiltradas.sortedByDescending { it.fechaCreacion }
                    else -> notasFiltradas.sortedBy { it.fechaCreacion }
                }
            }
            else -> uiState.items
        }
    }

    fun obtenerItemsPorText(filtro: String): List<Item> {
        val filtrado = uiState.items.filter{
            when(it)
            {
                is Item.Tarea -> it.titulo.contains(filtro, ignoreCase = true)
                is Item.Nota -> it.titulo.contains(filtro, ignoreCase = true)
            }
        }

        return filtrado
    }
    fun obtenerItemPorID(posicion: Int): Item
    {
        val updatedItems = uiState.items.toMutableList()
        return updatedItems.get(posicion)
    }
}