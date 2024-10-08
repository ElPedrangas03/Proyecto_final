package com.example.proyectofinal.ui

import android.app.TimePickerDialog
import android.icu.util.Calendar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agregar(navController: NavController, tareasNotasViewModel: TareasNotasViewModel) {
    val context = LocalContext.current

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
        var title by remember { mutableStateOf("") }
        var isNota by remember { mutableStateOf(true) }
        var content by remember { mutableStateOf("") }
        var dueDate by remember { mutableStateOf(LocalDate.now()) }  //Fecha predeterminada: hoy
        var dueTime by remember { mutableStateOf(LocalTime.now().withSecond(0).withNano(0)) }  //Hora predeterminada: thismoment
        var showDatePickerDialog by remember { mutableStateOf(false) }

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
                Text("Tipo de elemento:")
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isNota,
                        onClick = { isNota = true },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Nota")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !isNota,
                        onClick = { isNota = false },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Tarea")
                }
            }


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
                OutlinedTextField(
                    value = dueDate.toString(),
                    onValueChange = {},
                    label = { Text("Fecha de Vencimiento") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePickerDialog = true }) {
                            Icon(painter = painterResource(id = android.R.drawable.ic_menu_my_calendar), contentDescription = "Seleccionar fecha")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dueTime.toString(),
                    onValueChange = {},
                    label = { Text("Hora de Vencimiento") },
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
                            Icon(painter = painterResource(id = android.R.drawable.ic_menu_recent_history), contentDescription = "Seleccionar hora")
                        }
                    },
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

            Button(
                onClick = {
                    if (isNota) {
                        tareasNotasViewModel.agregarNota(title, LocalDateTime.now(), content)
                    } else {
                        val dueDateTime = LocalDateTime.of(dueDate, dueTime)
                        tareasNotasViewModel.agregarTarea(title, dueDateTime, LocalDateTime.now(), content)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Guardar")
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
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
