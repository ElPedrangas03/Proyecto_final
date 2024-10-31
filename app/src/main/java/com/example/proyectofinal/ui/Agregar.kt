package com.example.proyectofinal.ui

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Agregar(navController: NavController, tareasNotasViewModel: TareasNotasViewModel) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var isNota by remember { mutableStateOf(true) }
    var content by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(LocalDate.now()) }  //Fecha predeterminada: hoy
    var dueTime by remember { mutableStateOf(LocalTime.now().withSecond(0).withNano(0)) }  //Hora predeterminada: thismoment
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTabs by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.agregar_tarea_nota)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isNota) {
                            tareasNotasViewModel.agregarNota(title, LocalDateTime.now(), content)
                        } else {
                            val dueDateTime = LocalDateTime.of(dueDate, dueTime)
                            tareasNotasViewModel.agregarTarea(
                                title,
                                dueDateTime,
                                LocalDateTime.now(),
                                content
                            )
                        }
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Guardar")
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
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.titulo)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isNota) {
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(stringResource(R.string.contenido_de_la_nota)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                val context = LocalContext.current
                var imagesUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        //modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Top
                    ) {
                        CameraView(imagesUris = imagesUris, onImagesChanged = { newUris ->
                            imagesUris = imagesUris + newUris
                        })
                    }
                    Row(
                        //modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Top
                    ) {
                        CollectionGalleryView(
                            imagesUris = imagesUris,
                            onImagesChanged = { newUris ->
                                imagesUris = imagesUris + newUris
                            })
                    }
                    FlowRow(
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        imagesUris.forEach { uri ->
                            AsyncImage(
                                model = ImageRequest.Builder(context).data(uri)
                                    .crossfade(enable = true).build(),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                            )
                        }
                    }
                }


            } else {
                OutlinedTextField(
                    value = dueDate.toString(),
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
                    value = dueTime.toString(),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.hora_de_vencimiento)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    dueTime = LocalTime.of(hourOfDay, minute)
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
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(stringResource(R.string.descripcion)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                val context = LocalContext.current
                var imagesUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        //modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Top
                    ) {
                        CameraView(imagesUris = imagesUris, onImagesChanged = { newUris ->
                            imagesUris = imagesUris + newUris
                        })
                    }
                    Row(
                        //modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Top
                    ) {
                        CollectionGalleryView(
                            imagesUris = imagesUris,
                            onImagesChanged = { newUris ->
                                imagesUris = imagesUris + newUris
                            })
                    }
                    FlowRow(
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        imagesUris.forEach { uri ->
                            AsyncImage(
                                model = ImageRequest.Builder(context).data(uri)
                                    .crossfade(enable = true).build(),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isNota) {
                        tareasNotasViewModel.agregarNota(title, LocalDateTime.now(), content)
                    } else {
                        val dueDateTime = LocalDateTime.of(dueDate, dueTime)
                        tareasNotasViewModel.agregarTarea(
                            title,
                            dueDateTime,
                            LocalDateTime.now(),
                            content
                        )
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.guardar))
            }

        }

        if (showDatePickerDialog) {
            DatePickerModal(
                onDateSelected = { selectedDate ->
                    dueDate = selectedDate
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
        DatePicker(state = datePickerState)
    }
}
