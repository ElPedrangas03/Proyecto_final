package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalLayout(
    navController: NavController,
    tareasNotasViewModel: TareasNotasViewModel,
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val uiState = tareasNotasViewModel.uiState
    var filtroSeleccionado = rememberSaveable { mutableStateOf<String?>(null) }


    Scaffold(
        topBar = { TopBar(navController, "Itsur Notes", onSearchClick = { tareasNotasViewModel.buscarItems(it) }) },
        floatingActionButton = {
            BotonFlotante {
                navController.navigate("agregar") // Navegar a la pantalla de agregar tarea o nota
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            onTabSelected(index)
                            filtroSeleccionado.value = if (index == 0) {
                                "Fecha de vencimiento"
                            } else {
                                "Fecha de creación"
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }

            FilterButton(
                tabIndex = selectedTabIndex,
                filtroSeleccionado = filtroSeleccionado.value
            ) { filtro ->
                filtroSeleccionado.value = filtro
            }
            val itemsFiltrados = when (selectedTabIndex) {
                0 -> tareasNotasViewModel.obtenerItemsFiltrados(filtroSeleccionado.value ?: "Fecha de vencimiento", 0)
                1 -> tareasNotasViewModel.obtenerItemsFiltrados(filtroSeleccionado.value ?: "Fecha de creación", 1)
                else -> uiState.items
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(itemsFiltrados) { item ->
                    when (item) {
                        is Item.Tarea -> {
                            BoxTarea(
                                Titulo = item.titulo,
                                Fecha = item.fecha,
                                Fechacreacion = item.fechaCreacion,
                                Descripcion = item.descripcion,
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
                                Fechacreacion = item.fechaCreacion,
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
