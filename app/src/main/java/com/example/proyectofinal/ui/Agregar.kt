package com.example.proyectofinal.ui

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Nota
import com.example.proyectofinal.data.Tarea

import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Agregar(navController: NavController, viewModel: TareasNotasViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isNota by rememberSaveable { mutableStateOf(true) }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }

    var isButtonEnabled by remember { mutableStateOf(true) }
    var isNavigating by remember { mutableStateOf(false) }
    var shouldResetFields by remember { mutableStateOf(true) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.agregar_tarea_nota)) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isButtonEnabled && !isNavigating) {
                            isButtonEnabled = false
                            isNavigating = true
                            navController.popBackStack()
                            shouldResetFields = true
                            isButtonEnabled = true
                            isNavigating = false
                            viewModel.resetearCampos()
                        }

                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isButtonEnabled && !isNavigating) {
                    isButtonEnabled = false
                    scope.launch {
                        try {
                            if (isNota) {
                                viewModel.agregarNota(
                                    titulo = viewModel.title,
                                    fechaCreacion = LocalDateTime.now(),
                                    contenido = viewModel.content,
                                    imagenes = viewModel.imagesUris
                                )
                            } else {
                                val dueDateTime = LocalDateTime.of(viewModel.dueDate, viewModel.dueTime)
                                viewModel.agregarTarea(
                                    titulo = viewModel.title,
                                    fecha = dueDateTime,
                                    fechaCreacion = LocalDateTime.now(),
                                    descripcion = viewModel.content,
                                    imagenes = viewModel.imagesUris
                                )
                            }
                            isNavigating = true
                            shouldResetFields = false
                            navController.popBackStack()
                            viewModel.resetearCampos()
                        } catch (e: Exception) {
                            Log.e("Agregar", "Error al guardar: ", e)
                        } finally {
                            if (shouldResetFields) {
                                viewModel.resetearCampos()
                            }
                            isButtonEnabled = true
                            isNavigating = false
                        }
                    }
                }
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
                Text(stringResource(R.string.tipo_de_elemento))
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isNota,
                        onClick = { isNota = true },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text(stringResource(R.string.nota))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !isNota,
                        onClick = { isNota = false },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text(stringResource(R.string.tarea))
                }
            }

            TextField(
                value = viewModel.title,
                onValueChange = { newValue ->
                    viewModel.updateTitle(newValue)
                },
                label = { Text(stringResource(R.string.titulo)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isNota) {
                TextField(
                    value = viewModel.content,
                    onValueChange = {
                        viewModel.updateContent(it)
                    },
                    label = { Text(stringResource(R.string.contenido_de_la_nota)) },
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
                        CameraView(imagesUris = viewModel.imagesUris, onImagesChanged = { newUris ->
                            val uniqueUris = (viewModel.imagesUris + newUris).distinct()
                            viewModel.updateImagesUris(uniqueUris)
                        })
                        CollectionGalleryView(
                            imagesUris = viewModel.imagesUris,
                            onImagesChanged = { newUris ->
                                val uniqueUris = (viewModel.imagesUris + newUris).distinct()
                                viewModel.updateImagesUris(uniqueUris)
                            }
                        )
                        viewModel.imagesUris.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(uri)
                                        .crossfade(enable = true).build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                )
                                IconButton(
                                    onClick = { viewModel.removeImageUri(uri) },
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
                    value = viewModel.dueDate.toString(),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.fecha_de_vencimiento)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerDialog = true }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                                contentDescription = stringResource(R.string.seleccionar_fecha)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.dueTime.toString(),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.hora_de_vencimiento)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    viewModel.updateDueTime(LocalTime.of(hourOfDay, minute))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            )
                            timePicker.show()
                        }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                                contentDescription = stringResource(R.string.seleccionar_hora)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = viewModel.content,
                    onValueChange = {
                        viewModel.updateContent(it)
                    },
                    label = { Text(stringResource(R.string.descripcion)) },
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
                        CameraView(imagesUris = viewModel.imagesUris, onImagesChanged = { newUris ->
                            val uniqueUris = (viewModel.imagesUris + newUris).distinct()
                            viewModel.updateImagesUris(uniqueUris)
                        })
                        CollectionGalleryView(
                            imagesUris = viewModel.imagesUris,
                            onImagesChanged = { newUris ->
                                val uniqueUris = (viewModel.imagesUris + newUris).distinct()
                                viewModel.updateImagesUris(uniqueUris)
                            }
                        )
                        viewModel.imagesUris.forEach { uri ->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(uri)
                                        .crossfade(enable = true).build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                )
                                IconButton(
                                    onClick = { viewModel.removeImageUri(uri) },
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
            DatePickerModal(
                onDateSelected = { selectedDate ->
                    viewModel.updateDueDate(selectedDate)
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
fun DatePickerModal(
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

                    onDateSelected(selectedDate)
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
        // Aquí añadimos el verticalScroll
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
