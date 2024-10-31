package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinal.R
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Buscar(
    navController: NavController,
    tareasNotasViewModel: TareasNotasViewModel,
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
){
    val uiState = tareasNotasViewModel.uiState
    var search by remember{ mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = search,
                        onValueChange = {
                            search = it
                        },
                        label = { Text(stringResource(id = R.string.buscar)) },
                        modifier = Modifier.fillMaxWidth()
                    ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ){
        paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            /*TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            onTabSelected(index)
                        },
                        text = { Text(title) }
                    )
                }
            }*/
            val itemsFiltrados = tareasNotasViewModel.obtenerItemsPorText(search)
            LazyColumn (
                modifier = Modifier.fillMaxWidth()
            ){
                var marcadorTarea=false
                var marcadorNota=false
                items(itemsFiltrados) { item ->
                    when (item) {
                        is Item.Tarea -> {
                            if(!marcadorTarea){
                                Text(
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    text = stringResource(id = R.string.tarea)+"s"
                                )
                                marcadorTarea=true
                            }
                            BoxTarea(
                                Tarea = item,
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
                                onEdit = {
                                    navController.navigate("itemEditar/${uiState.items.indexOf(item)}")
                                },
                                onDelete = {
                                    val index = uiState.items.indexOf(item)
                                    tareasNotasViewModel.eliminarItem(index)
                                }
                            )
                        }
                        is Item.Nota -> {
                            if(!marcadorNota){
                                Text(
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    text = stringResource(id = R.string.nota)+"s"
                                )
                                marcadorNota=true
                            }
                            BoxNota(
                                Titulo = item.titulo,
                                Fechacreacion = item.fechaCreacion,
                                Contenido = item.contenido,
                                onCardClick = {
                                    navController.navigate("item/${item.titulo}")
                                },
                                onEdit = {
                                    navController.navigate("itemEditar/${uiState.items.indexOf(item)}")
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
