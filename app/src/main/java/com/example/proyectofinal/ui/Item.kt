package com.example.proyectofinal.ui

import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

sealed class Item {
    data class Tarea(
        val titulo: String,
        val fecha: LocalDateTime,
        val fechaCreacion: LocalDateTime,
        var descripcion: String = "",
        val multimedia: List<MultimediaItem> = listOf(),
        val recordatorios: List<String> = listOf(),
        var completada: Boolean = false
    ) : Item()

    data class Nota(
        val titulo: String,
        val fechaCreacion: LocalDateTime,
        val contenido: String = "",
        val multimedia: List<MultimediaItem> = listOf()
    ) : Item()
}

data class MultimediaItem(
    val id: String = UUID.randomUUID().toString(),
    val tipo: MultimediaTipo,
    val descripcion: String,
    val uri: String
)

enum class MultimediaTipo {
    IMAGEN, VIDEO, AUDIO
}