package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun BoxTarea(
    Titulo: String,
    Fecha: LocalDateTime,
    Fechacreacion: LocalDateTime,
    Descripcion: String,
    onCardClick: () -> Unit,
    onComplete: () -> Unit,
    onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onCardClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(Titulo, style = MaterialTheme.typography.headlineSmall)
            Text(Fecha.toString(), style = MaterialTheme.typography.bodyMedium)
            Text(Descripcion, style = MaterialTheme.typography.bodyMedium)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onComplete) {
                    Icon(Icons.Default.Done, contentDescription = "Completar tarea")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar tarea")
                }
            }
        }
    }
}
