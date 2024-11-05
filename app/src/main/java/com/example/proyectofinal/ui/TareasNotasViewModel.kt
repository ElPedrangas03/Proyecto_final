package com.example.proyectofinal.ui

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Nota
import com.example.proyectofinal.data.NotaRepository
import com.example.proyectofinal.data.Tarea
import com.example.proyectofinal.data.TareaRepository
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class TareasNotasViewModel(
    private val tareaRepository: TareaRepository,
    private val notaRepository: NotaRepository
) : ViewModel() {
    var uiState by mutableStateOf(TareasNotasUiState())
        private set

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var dueDate by mutableStateOf(LocalDate.now())
        private set

    var dueTime by mutableStateOf(LocalTime.now().withSecond(0).withNano(0))
        private set

    var imagesUris by mutableStateOf<List<Uri>>(emptyList())
        private set

    init {
        // Cargar tareas y notas desde los repositorios usando flujos
        viewModelScope.launch {
            launch {
                tareaRepository.getAllTareasStream().collect { tareas ->
                    uiState = uiState.copy(tareas = tareas)
                }
            }
            launch {
                notaRepository.getAllNotasStream().collect { notas ->
                    uiState = uiState.copy(notas = notas)
                }
            }
        }
    }

    fun resetearCampos() {
        title = ""
        content = ""
        dueDate = LocalDate.now()
        dueTime = LocalTime.now().withSecond(0).withNano(0)
        imagesUris = emptyList()
    }

    fun completarTarea(tarea: Tarea) {
        viewModelScope.launch {
            val updatedTarea = tarea.copy(completada = true)
            tareaRepository.updateTarea(updatedTarea)
            actualizarTareas()
        }
    }

    fun eliminarItem(item: Any) {
        viewModelScope.launch {
            when (item) {
                is Tarea -> tareaRepository.deleteTarea(item)
                is Nota -> notaRepository.deleteNota(item)
            }
            actualizarTareas()
            actualizarNotas()
        }
    }

    fun agregarTarea(titulo: String, fecha: LocalDateTime, fechaCreacion: LocalDateTime, descripcion: String, imagenes: List<Uri>) {
        viewModelScope.launch {
            val multimediaJson = convertUrisToJson(imagenes)
            val tarea = Tarea(
                titulo = titulo,
                fecha = fecha.toString(),
                fechaCreacion = fechaCreacion.toString(),
                descripcion = descripcion,
                multimedia = multimediaJson
            )
            tareaRepository.insertTarea(tarea)
            actualizarTareas()
        }
    }

    fun agregarNota(titulo: String, fechaCreacion: LocalDateTime, contenido: String, imagenes: List<Uri>) {
        viewModelScope.launch {
            Log.d("ViewModel", "Título: $titulo, Contenido: $contenido, Imágenes: ${imagenes.size}")
            val multimediaJson = convertUrisToJson(imagenes)
            val nota = Nota(
                titulo = titulo,
                fechaCreacion = fechaCreacion.toString(),
                contenido = contenido,
                multimedia = multimediaJson
            )
            notaRepository.insertNota(nota)
            actualizarNotas()
        }
    }

    fun convertUrisToJson(uris: List<Uri>): String {
        val jsonArray = JSONArray()
        uris.forEach { uri ->
            jsonArray.put(uri.toString())
        }
        return jsonArray.toString()
    }

    fun editarTarea(tarea: Tarea) {
        viewModelScope.launch {
            tareaRepository.updateTarea(tarea)
            actualizarTareas()
        }
    }

    fun editarNota(nota: Nota) {
        viewModelScope.launch {
            notaRepository.updateNota(nota)
            actualizarNotas()
        }
    }

    private fun actualizarTareas() {
        viewModelScope.launch {
            tareaRepository.getAllTareasStream().collect { tareas ->
                uiState = uiState.copy(tareas = tareas)
            }
        }
    }

    private fun actualizarNotas() {
        viewModelScope.launch {
            notaRepository.getAllNotasStream().collect { notas ->
                uiState = uiState.copy(notas = notas)
            }
        }
    }

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateContent(newContent: String) {
        content = newContent
    }

    fun updateDueDate(newDate: LocalDate) {
        dueDate = newDate
    }

    fun updateDueTime(newTime: LocalTime) {
        dueTime = newTime
    }

    fun updateImagesUris(newUris: List<Uri>) {
        imagesUris = newUris
    }

    fun buscarItems(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }

    @Composable
    fun obtenerItemsFiltrados(filtro: String, tabIndex: Int, mostrarCompletadas: Boolean): List<Any> {
        return when (tabIndex) {
            0 -> {
                val tareasFiltradas = uiState.tareas
                val tareasVisibles = if (mostrarCompletadas) {
                    tareasFiltradas.filter { it.completada } // Mostrar solo las tareas completadas
                } else {
                    tareasFiltradas.filter { !it.completada } // Mostrar solo las tareas pendientes
                }

                when (filtro) {
                    stringResource(R.string.titulo) -> tareasVisibles.sortedBy { it.titulo }
                    stringResource(R.string.fecha_de_creacion) -> tareasVisibles.sortedByDescending { it.fechaCreacion }
                    stringResource(R.string.fecha_de_vencimiento) -> tareasVisibles.sortedBy { it.fecha }
                    else -> tareasVisibles.sortedBy { it.fecha }
                }
            }
            1 -> { // Para Notas (tabIndex == 1)
                val notasFiltradas = uiState.notas
                when (filtro) {
                    stringResource(R.string.titulo) -> notasFiltradas.sortedBy { it.titulo }
                    stringResource(R.string.fecha_de_creacion) -> notasFiltradas.sortedByDescending { it.fechaCreacion }
                    else -> notasFiltradas.sortedBy { it.fechaCreacion }
                }
            }
            else -> uiState.tareas + uiState.notas
        }
    }

    fun obtenerItemsPorText(filtro: String): List<Any> {
        val tareasFiltradas = uiState.tareas.filter { it.titulo.contains(filtro, ignoreCase = true) }
        val notasFiltradas = uiState.notas.filter { it.titulo.contains(filtro, ignoreCase = true) }
        return tareasFiltradas + notasFiltradas
    }

    fun obtenerItemPorID(id: String): Any? {
        return uiState.tareas.find { it.id == id } ?: uiState.notas.find { it.id == id }
    }

    fun removeImageUri(uri: Uri) {
        imagesUris = imagesUris.filter { it != uri }
    }

    fun parseMultimediaUris(multimedia: String): List<Uri> {
        val uris = mutableListOf<Uri>()
        if (multimedia.isNotEmpty()) {
            try {
                val jsonArray = JSONArray(multimedia)
                for (i in 0 until jsonArray.length()) {
                    val uriString = jsonArray.optString(i, null)
                    if (uriString != null) {
                        uris.add(Uri.parse(uriString))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return uris
    }

}
