package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.crossfade
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Nota
import com.example.proyectofinal.data.Tarea

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ItemLayout(navController: NavController, tareasNotasViewModel: TareasNotasViewModel, titulo: String) {
    val item = tareasNotasViewModel.uiState.tareas.find { it.titulo == titulo }
        ?: tareasNotasViewModel.uiState.notas.find { it.titulo == titulo }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detalles)) },
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
            item?.let {
                when (it) {
                    is Tarea -> {
                        Text("${stringResource(id = R.string.titulo)}: ${it.titulo}", style = MaterialTheme.typography.headlineSmall)
                        Text("${stringResource(R.string.fecha)}: ${it.fecha}", style = MaterialTheme.typography.bodyMedium)
                        Text("${stringResource(R.string.descripcion)}: ${it.descripcion}", style = MaterialTheme.typography.bodyMedium)
                        val imageUris = tareasNotasViewModel.parseMultimediaUris(it.multimedia)
                        if (imageUris.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                imageUris.forEach { uri ->
                                    AsyncImage(
                                        model = coil3.request.ImageRequest.Builder(LocalContext.current).data(uri)
                                            .crossfade(enable = true).build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(120.dp)
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                    }
                    is Nota -> {
                        Text("${stringResource(id = R.string.titulo)}: ${it.titulo}", style = MaterialTheme.typography.headlineSmall)
                        Text("${stringResource(id = R.string.contenido)}: ${it.contenido}", style = MaterialTheme.typography.bodyMedium)
                        val imageUris = tareasNotasViewModel.parseMultimediaUris(it.multimedia)
                        if (imageUris.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                imageUris.forEach { uri ->
                                    AsyncImage(
                                        model = coil3.request.ImageRequest.Builder(LocalContext.current).data(uri)
                                            .crossfade(enable = true).build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(120.dp)
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            } ?: run {
                Text(stringResource(R.string.elemento_no_encontrado), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
