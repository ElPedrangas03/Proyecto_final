package com.example.proyectofinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agregar(navController: NavController, tareasNotasViewModel: TareasNotasViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Tarea/Nota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            var title by remember { mutableStateOf("") }
            var isNota by remember { mutableStateOf(false) }
            var content by remember { mutableStateOf("") }
            var dueDate by remember { mutableStateOf("") }

            // Selector de tipo de elemento (Nota o Tarea)
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Agregar Nota")
                Switch(
                    checked = isNota,
                    onCheckedChange = { isNota = it }
                )
                Text("Agregar Tarea")
            }

            // Campos de texto para ingresar los detalles de la tarea o nota
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (isNota) {
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido de la Nota") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                MultimediaPicker(onMediaSelected = { /* TODO: Handle media selection */ })
            } else {
                TextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Fecha de Vencimiento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                MultimediaPicker(onMediaSelected = { /* TODO: Handle media selection */ })
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para guardar la tarea o nota
            Button(
                onClick = {
                    if (isNota) {
                        tareasNotasViewModel.agregarNota(title, content)
                    } else {
                        tareasNotasViewModel.agregarTarea(title, dueDate, content)
                    }
                    navController.popBackStack() // Navegar de vuelta a la pantalla principal
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Guardar")
            }
        }
    }
}