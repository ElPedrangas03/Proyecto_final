package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemLayout(navController: NavController, tareasNotasViewModel: TareasNotasViewModel, titulo: String) {
    val item = tareasNotasViewModel.uiState.items.find {
        when (it) {
            is Item.Tarea -> it.titulo == titulo
            is Item.Nota -> it.titulo == titulo
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
        })}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item?.let {
                when (it) {
                    is Item.Tarea -> {
                        Text("Título: ${it.titulo}", style = MaterialTheme.typography.headlineSmall)
                        Text("Fecha: ${it.fecha}", style = MaterialTheme.typography.bodyMedium)
                        Text("Descripción: ${it.descripcion}", style = MaterialTheme.typography.bodyMedium)
                    }
                    is Item.Nota -> {
                        Text("Título: ${it.titulo}", style = MaterialTheme.typography.headlineSmall)
                        Text("Contenido: ${it.contenido}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } ?: run {
                Text("Elemento no encontrado", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}