package com.example.proyectofinal.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "alarmas")
data class Alarma(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(), // Identificador único
    @ColumnInfo(name = "tarea_id") val tareaId: String, // Vincular con una tarea
    @ColumnInfo(name = "fecha") val fecha: String, // Fecha (yyyy-MM-dd)
    @ColumnInfo(name = "hora") val hora: String, // Hora (HH:mm)
    @ColumnInfo(name = "mensaje") val mensaje: String = "", // Mensaje a mostrar al activarse
    @ColumnInfo(name = "repetitiva") val repetitiva: Boolean = false, // Indica si es repetitiva
    @ColumnInfo(name = "activa") val activa: Boolean = true // Estado de la alarma
)
