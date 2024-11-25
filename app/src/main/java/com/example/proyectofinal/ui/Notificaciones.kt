package com.example.proyectofinal.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.R
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun notificaciones(navController: NavController, viewModel: TareasNotasViewModel) {
    // Usamos directamente la lista de notificaciones del ViewModel
    val reminders = viewModel.notifications
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var tempDate by remember { mutableStateOf<LocalDate?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.notificaciones)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDatePicker = true
                isEditing = false
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar recordatorio")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Hola, estas en la sección de Notificaciones",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar las notificaciones existentes
            reminders.forEachIndexed { index, (date, time) ->
                key(index) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Recordatorio ${index + 1}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Fecha",
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = date.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(42.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Done,
                                            contentDescription = "Hora",
                                            modifier = Modifier.size(24.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${time.hour}:${time.minute.toString().padStart(2, '0')}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }

                            // Menú desplegable para editar
                            var expanded by remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 46.dp, end = 8.dp)
                            ) {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Build,
                                        contentDescription = "Editar",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            editingIndex = index
                                            isEditing = true
                                            showDatePicker = true
                                            expanded = false
                                        },
                                        text = { Text("Editar Fecha") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            editingIndex = index
                                            isEditing = true
                                            showTimePicker = true
                                            expanded = false
                                        },
                                        text = { Text("Editar Hora") }
                                    )
                                }
                            }

                            // Botón para eliminar la notificación
                            IconButton(
                                onClick = { viewModel.eliminarNotificacion(index) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        // Diálogo para seleccionar la fecha
        if (showDatePicker) {
            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val nuevaFecha = LocalDate.of(year, month + 1, dayOfMonth)
                    if (isEditing) {
                        editingIndex?.let { index ->
                            viewModel.editarNotificacionFecha(index, nuevaFecha)
                        }
                    } else {
                        tempDate = nuevaFecha
                        showTimePicker = true
                    }
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                setOnCancelListener {
                    showDatePicker = false // Restablecer el estado al cancelar
                }
            }.show()
        }

        // Diálogo para seleccionar la hora
        if (showTimePicker) {
            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val nuevaHora = LocalTime.of(hourOfDay, minute)
                    if (isEditing) {
                        editingIndex?.let { index ->
                            viewModel.editarNotificacionHora(index, nuevaHora)
                        }
                    } else {
                        tempDate?.let { date ->
                            viewModel.agregarNotificacion(date, nuevaHora)
                        }
                    }
                    tempDate = null
                    showTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).apply {
                setOnCancelListener {
                    showTimePicker = false // Restablecer el estado al cancelar
                }
            }.show()
        }

        // Limpieza al desmontar el Composable
        DisposableEffect(Unit) {
            onDispose {
                showDatePicker = false
                showTimePicker = false
                isEditing = false
                editingIndex = null
            }
        }
    }
}