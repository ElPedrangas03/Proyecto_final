package com.example.proyectofinal.ui

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Nota
import com.example.proyectofinal.data.Tarea
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Editar(
    navController: NavController,
    tareasNotasViewModel: TareasNotasViewModel,
    idItem: String
) {
    val context = LocalContext.current
    val tareaNota = tareasNotasViewModel.obtenerItemPorID(idItem)

    if (tareaNota == null) {
        tareasNotasViewModel.resetearCampos()
        navController.popBackStack()
        return
    }

    tareaNota.let {
        if (it is Tarea) {
            tareasNotasViewModel.updateTitle(it.titulo)
            tareasNotasViewModel.updateContent(it.descripcion)
            it.fecha?.let { fecha ->
                tareasNotasViewModel.updateDueDate(LocalDateTime.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate())
            }
            it.fechaCreacion?.let { fechaCreacion ->
                tareasNotasViewModel.updateDueTime(LocalDateTime.parse(fechaCreacion, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime())
            }
            tareasNotasViewModel.updateImagesUris(tareasNotasViewModel.parseMultimediaUris(it.multimedia))
        } else if (it is Nota) {
            tareasNotasViewModel.updateTitle(it.titulo)
            tareasNotasViewModel.updateContent(it.contenido)
            tareasNotasViewModel.updateImagesUris(tareasNotasViewModel.parseMultimediaUris(it.multimedia))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tareasNotasViewModel.resetearCampos()
        }
    }

    val title by tareasNotasViewModel::title
    val content by tareasNotasViewModel::content
    val dueDate by tareasNotasViewModel::dueDate
    val dueTime by tareasNotasViewModel::dueTime
    val imagesUris by tareasNotasViewModel::imagesUris
    val isNota by remember { mutableStateOf(tareaNota is Nota) }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.editar_tarea_nota)) },
                navigationIcon = {
                    IconButton(onClick = {
                        tareasNotasViewModel.resetearCampos()
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isNota) {
                    tareasNotasViewModel.editarNota(
                        Nota(
                            id = idItem,
                            titulo = title,
                            fechaCreacion = (tareaNota as Nota).fechaCreacion,
                            contenido = content,
                            multimedia = tareasNotasViewModel.convertUrisToJson(imagesUris)
                        )
                    )
                } else {
                    val dueDateTime = LocalDateTime.of(dueDate, dueTime)
                    tareasNotasViewModel.editarTarea(
                        Tarea(
                            id = idItem,
                            titulo = title,
                            fecha = dueDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            fechaCreacion = (tareaNota as Tarea).fechaCreacion,
                            descripcion = content,
                            multimedia = tareasNotasViewModel.convertUrisToJson(imagesUris)
                        )
                    )
                }
                tareasNotasViewModel.resetearCampos()
                navController.navigateUp()
            }) {
                Icon(Icons.Default.Done, contentDescription = "Guardar")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.tipo_de_elemento))
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isNota,
                        onClick = { /* No se permite cambiar el tipo durante la edición */ },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text(stringResource(id = R.string.nota))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !isNota,
                        onClick = { /* No se permite cambiar el tipo durante la edición */ },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text(stringResource(id = R.string.tarea))
                }
            }

            TextField(
                value = title,
                onValueChange = { tareasNotasViewModel.updateTitle(it) },
                label = { Text(stringResource(id = R.string.titulo)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isNota) {
                TextField(
                    value = content,
                    onValueChange = { tareasNotasViewModel.updateContent(it) },
                    label = { Text(stringResource(id = R.string.contenido_de_la_nota)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        CameraView(imagesUris = imagesUris, onImagesChanged = { newUris ->
                            val uniqueUris = (imagesUris + newUris).distinct()
                            tareasNotasViewModel.updateImagesUris(uniqueUris)
                        })
                        CollectionGalleryView(
                            imagesUris = imagesUris,
                            onImagesChanged = { newUris ->
                                val uniqueUris = (imagesUris + newUris).distinct()
                                tareasNotasViewModel.updateImagesUris(uniqueUris)
                            }
                        )
                        imagesUris.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(uri)
                                        .crossfade(true).build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                )
                                IconButton(
                                    onClick = {
                                        tareasNotasViewModel.updateImagesUris(imagesUris.filter { it != uri })
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar imagen",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                OutlinedTextField(
                    value = dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.fecha_de_vencimiento)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerDialog = true }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                                contentDescription = stringResource(id = R.string.seleccionar_fecha)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dueTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.hora_de_vencimiento)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    tareasNotasViewModel.updateDueTime(LocalTime.of(hourOfDay, minute))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            )
                            timePicker.show()
                        }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                                contentDescription = stringResource(id = R.string.seleccionar_hora)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { tareasNotasViewModel.updateContent(it) },
                    label = { Text(stringResource(id = R.string.descripcion)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        CameraView(imagesUris = imagesUris, onImagesChanged = { newUris ->
                            val uniqueUris = (imagesUris + newUris).distinct()
                            tareasNotasViewModel.updateImagesUris(uniqueUris)
                        })
                        CollectionGalleryView(
                            imagesUris = imagesUris,
                            onImagesChanged = { newUris ->
                                val uniqueUris = (imagesUris + newUris).distinct()
                                tareasNotasViewModel.updateImagesUris(uniqueUris)
                            }
                        )
                        imagesUris.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(uri)
                                        .crossfade(true).build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                )
                                IconButton(
                                    onClick = {
                                        tareasNotasViewModel.updateImagesUris(imagesUris.filter { it != uri })
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar imagen",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showDatePickerDialog) {
            DatePickerModalEditar(
                onDateSelected = { selectedDate ->
                    tareasNotasViewModel.updateDueDate(selectedDate)
                },
                onDismiss = {
                    showDatePickerDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalEditar(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedMillis = datePickerState.selectedDateMillis
                if (selectedMillis != null) {
                    val selectedDate = LocalDateTime.ofEpochSecond(
                        selectedMillis / 1000,
                        0,
                        ZoneOffset.UTC
                    ).toLocalDate()

                    onDateSelected(selectedDate)  // Devuelve la fecha seleccionada
                } else {
                    onDateSelected(LocalDate.now())
                }
                onDismiss()
            }) {
                Text(stringResource(R.string.aceptar))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancelar))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

