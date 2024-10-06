package com.example.proyectofinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalLayout(
    navController: NavController,
    tareasNotasViewModel: TareasNotasViewModel,
    mostrarTareas: Boolean
) {
    val uiState = tareasNotasViewModel.uiState

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            FilterButton()

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                val itemsToShow = if (mostrarTareas) {
                    uiState.items.filterIsInstance<Item.Tarea>()
                } else {
                    uiState.items.filterIsInstance<Item.Nota>()
                }

                items(itemsToShow) { item ->
                    when (item) {
                        is Item.Tarea -> {
                            BoxTarea(
                                Titulo = item.titulo,
                                Fecha = item.fecha,
                                onCardClick = {
                                    navController.navigate("item/${item.titulo}")
                                },
                                onComplete = {
                                    val index = uiState.items.indexOf(item)
                                    tareasNotasViewModel.completarTarea(index)
                                },
                                onDelete = {
                                    val index = uiState.items.indexOf(item)
                                    tareasNotasViewModel.eliminarItem(index)
                                }
                            )
                        }
                        is Item.Nota -> {
                            BoxNota(
                                Titulo = item.titulo,
                                Contenido = item.contenido,
                                onCardClick = {
                                    navController.navigate("item/${item.titulo}")
                                },
                                onDelete = {
                                    val index = uiState.items.indexOf(item)
                                    tareasNotasViewModel.eliminarItem(index)
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}