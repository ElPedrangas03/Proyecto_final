package com.example.proyectofinal.ui


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.proyectofinal.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.File
import java.util.*


private var mediaPlayer: MediaPlayer? = null

// Variables globales para la grabación de audio
private var mediaRecorder: MediaRecorder? = null
private var audioFilePath: String? = null

// Función para reproducir audio con validación
fun playAudio(context: Context, path: String) {
    try {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(path)
            prepare()
            start()
        }
        Toast.makeText(context, "Reproduciendo audio...", Toast.LENGTH_SHORT).show()

        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
            Toast.makeText(context, "Audio finalizado", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al reproducir el audio: ${e.message}", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

// Iniciar grabación sin permisos explícitos
//fun startRecordingWithoutPermissions(context: Context) {
//    try {
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        //val storageDir = context.filesDir // Almacena en el directorio interno de la app
//        val storageDir = context.getExternalFilesDir("images")
//        //val audioFile = File(storageDir, "audio_${System.currentTimeMillis()}.mp3")
//        val audioName = "audio_$timeStamp.mp3"
//        val audioFile = File(storageDir, audioName)
//        audioFilePath = audioFile.absolutePath
//
//        mediaRecorder = MediaRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//            setOutputFile(audioFilePath)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//            prepare()
//            start()
//        }
//    } catch (e: Exception) {
//        Toast.makeText(context, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
//        e.printStackTrace()
//    }
//}
fun startRecordingWithoutPermissions(context: Context) {
    try {
        val audioFile = context.createAudioFile() // Llama a la nueva función
        audioFilePath = audioFile.absolutePath // Obtén la ruta absoluta del archivo

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(audioFilePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
            start()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

// Detener grabación
fun stopRecording(): String? {
    return try {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        audioFilePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Manejo de permisos
fun checkAndRequestPermissions(context: Context, onPermissionsGranted: () -> Unit) {
    val requiredPermissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val missingPermissions = requiredPermissions.filter {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }

    if (missingPermissions.isEmpty()) {
        onPermissionsGranted()
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            missingPermissions.toTypedArray(),
            100
        )
    }
}

// Componente para manejar grabación y reproducción de audio
//@Composable
//fun AudioHandler(
//    imagesUris: List<Uri>,
//    onImagesChanged: (List<Uri>) -> Unit
//) {
//    val context = LocalContext.current
//    var isRecording by remember { mutableStateOf(false) }
//    var audioFilePaths by remember { mutableStateOf<List<String>>(emptyList()) }
//
//    // Función para iniciar/detener grabación
//    val toggleRecording: () -> Unit = {
//        if (isRecording) {
//            stopRecording()?.let { path ->
//                audioFilePaths = audioFilePaths + path
//                Toast.makeText(context, "Audio guardado en: $path", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            startRecordingWithoutPermissions(context)
//            Toast.makeText(context, "Grabando audio...", Toast.LENGTH_SHORT).show()
//        }
//        isRecording = !isRecording
//    }
//
//    Column {
//        IconButton(onClick = toggleRecording) {
//            Icon(
//                painter = painterResource(id = if (isRecording) R.drawable.stop else R.drawable.micro),
//                contentDescription = "Grabar audio"
//            )
//        }
//
//        // Reproducir audios guardados
//        audioFilePaths.forEachIndexed { index, path ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Audio ${index + 1}", modifier = Modifier.weight(1f))
//                IconButton(onClick = { playAudio(context, path) }) {
//                    Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir audio")
//                }
//            }
//        }
//    }
//}
//}
//@Composable
//fun AudioHandler(
//    imagesUris: List<Uri>,
//    onImagesChanged: (List<Uri>) -> Unit
//) {
//    val context = LocalContext.current
//    var isRecording by remember { mutableStateOf(false) }
//
//    // Lanzador para manejar la solicitud de permisos
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val allGranted = permissions.values.all { it } // Verifica si todos los permisos fueron otorgados
//        if (allGranted) {
//            // Inicia la grabación si los permisos son concedidos
//            startRecordingWithoutPermissions(context)
//            Toast.makeText(context, "Grabando audio...", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Permisos necesarios no otorgados", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Función para iniciar/detener grabación
//    val toggleRecording: () -> Unit = {
//        if (isRecording) {
//            stopRecording()?.let { path ->
//                val uri = Uri.parse(path)
//                // Actualizar la lista de URIs
//                //onImagesChanged(imagesUris + uri)
//                onImagesChanged(imagesUris + Uri.fromFile(context.lastCapturedFile))
//                Toast.makeText(context, "Audio guardado en: $path", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            // Verificar y solicitar permisos
//            checkAndRequestPermissions(context) {
//                // Si los permisos son concedidos, iniciar la grabación
//                startRecordingWithoutPermissions(context)
//                Toast.makeText(context, "Grabando audio...", Toast.LENGTH_SHORT).show()
//            }
//        }
//        isRecording = !isRecording
//    }
//
//    Column {
//        IconButton(onClick = toggleRecording) {
//            Icon(
//                painter = painterResource(id = if (isRecording) R.drawable.stop else R.drawable.micro),
//                contentDescription = "Grabar audio"
//            )
//        }
//
//        // Reproducir audios guardados
//        imagesUris.forEachIndexed { index, uri ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Audio ${index + 1}", modifier = Modifier.weight(1f))
//                IconButton(onClick = { playAudio(context, uri.toString()) }) {
//                    Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir audio")
//                }
//                IconButton(onClick = {
//                    // Eliminar audio de la lista
//                    onImagesChanged(imagesUris.filter { it != uri })
//                }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Eliminar audio", tint = Color.Red)
//                }
//            }
//        }
//    }
//}



//@Composable
//fun AudioHandler(
//    imagesUris: List<Uri>,
//    onImagesChanged: (List<Uri>) -> Unit
//) {
//    val context = LocalContext.current
//    var isRecording by remember { mutableStateOf(false) }
//    var recordingPath by remember { mutableStateOf<String?>(null) } // Para almacenar la ruta del audio grabado
//
//    // Lanzador para permisos
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val allGranted = permissions.values.all { it }
//        if (allGranted) {
//            // Si se concedieron los permisos, iniciar la grabación
//            startRecordingWithoutPermissions(context)
//            Toast.makeText(context, "Grabando audio...", Toast.LENGTH_SHORT).show()
//            isRecording = true
//        } else {
//            Toast.makeText(context, "Permisos necesarios no otorgados", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Función para iniciar/detener grabación
//    val toggleRecording: () -> Unit = {
//        if (isRecording) {
//            recordingPath = stopRecording() // Guarda la ruta del audio grabado
//            recordingPath?.let { path ->
//                val uri = Uri.parse(path)
//                // Actualizar la lista de URIs
//                onImagesChanged(imagesUris + uri)
//                Toast.makeText(context, "Audio guardado en: $path", Toast.LENGTH_SHORT).show()
//            }
//            isRecording = false // Cambiar el estado de grabación
//        } else {
//            // Solicitar permisos antes de grabar
//            permissionLauncher.launch(arrayOf(
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ))
//        }
//    }
//
//    Column {
//        IconButton(onClick = toggleRecording) {
//            Icon(
//                painter = painterResource(id = if (isRecording) R.drawable.stop else R.drawable.micro),
//                contentDescription = "Grabar audio"
//            )
//        }
//
//        // Reproducir audios guardados
//        imagesUris.forEachIndexed { index, uri ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Audio ${index + 1}", modifier = Modifier.weight(1f))
//                IconButton(onClick = { playAudio(context, uri.toString()) }) {
//                    Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir audio")
//                }
//                IconButton(onClick = {
//                    // Eliminar audio de la lista
//                    onImagesChanged(imagesUris.filter { it != uri })
//                }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Eliminar audio", tint = Color.Red)
//                }
//            }
//        }
//    }
//}

@Composable
fun AudioHandler(
    imagesUris: List<Uri>,
    onImagesChanged: (List<Uri>) -> Unit
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var recordingPath by remember { mutableStateOf<String?>(null) }

    // Lanzador para permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startRecordingWithoutPermissions(context)
            Toast.makeText(context, "Grabando audio...", Toast.LENGTH_SHORT).show()
            isRecording = true
        } else {
            Toast.makeText(context, "Permisos necesarios no otorgados", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para iniciar/detener grabación
    val toggleRecording: () -> Unit = {
        if (isRecording) {
            recordingPath = stopRecording() // Guarda la ruta del audio grabado
            recordingPath?.let { path ->
                val uri = Uri.fromFile(File(path)) // Crea la URI a partir de la ruta
                onImagesChanged(imagesUris + uri) // Actualiza la lista de URIs
                Log.d("AudioHandler", "Nuevas URIs: ${imagesUris + uri}") // Log para verificar
                Toast.makeText(context, "Audio guardado en: ${imagesUris + uri}", Toast.LENGTH_SHORT).show()
            }
            isRecording = false
        } else {
            permissionLauncher.launch(arrayOf(
                Manifest.permission.RECORD_AUDIO,
                //Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
        }
    }

    Column {
        IconButton(onClick = toggleRecording) {
            Icon(
                painter = painterResource(id = if (isRecording) R.drawable.stop else R.drawable.micro),
                contentDescription = "Grabar audio"
            )
        }

        // Reproducir audios guardados
//        imagesUris.forEachIndexed { index, uri ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Audio ${index + 1}", modifier = Modifier.weight(1f))
//                IconButton(onClick = { playAudio(context, uri.toString()) }) {
//                    Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir audio")
//                }
//                IconButton(onClick = {
//                    onImagesChanged(imagesUris.filter { it != uri }) // Eliminar audio de la lista
//                }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Eliminar audio", tint = Color.Red)
//                }
//            }
//        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createAudioFile(): File {
    val timeStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val audioFileName = "audio_$timeStamp"
    val storageDir = getExternalFilesDir("images")

    if (storageDir != null && !storageDir.exists()) {
        storageDir.mkdirs()
    }

    return File.createTempFile(
        audioFileName,
        ".mp3",
        storageDir ?: cacheDir
    )
}