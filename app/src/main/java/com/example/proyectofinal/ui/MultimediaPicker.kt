package com.example.proyectofinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MultimediaPicker(onMediaSelected: (MultimediaItem) -> Unit) {
    // Placeholder para el selector de multimedia
    Button(
        onClick = {
            // Aquí iría la lógica para seleccionar el archivo multimedia
            val dummyMedia = MultimediaItem(
                tipo = MultimediaTipo.IMAGEN,
                descripcion = "Imagen de ejemplo",
                uri = "file://example.jpg"
            )
            onMediaSelected(dummyMedia)
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Seleccionar multimedia")
    }
}