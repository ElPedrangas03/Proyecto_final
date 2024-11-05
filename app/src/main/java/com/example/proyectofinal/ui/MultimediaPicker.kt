package com.example.proyectofinal.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R


@Composable
fun AbrirCamara(onMediaSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider", file
    )

    var image by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            image?.let { onMediaSelected(it) } // Llama al callback con la URI de la imagen
        }
    }

    Button(
        onClick = {
            cameraLauncher.launch(uri) // Abre la cámara
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(stringResource(id = R.string.tomar_foto))
    }

    // Muestra la imagen tomada, si existe
    image?.let { uri ->
        Image(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null
        )
    }
}


@Composable
fun MultimediaPicker(onMediaSelected: (MultimediaItem) -> Unit) {
    val context = LocalContext.current
    // Placeholder para el selector de multimedia de galeria
    Button(
        onClick = {
            // Aquí iría la lógica para seleccionar el archivo multimedia
            val dummyMedia = MultimediaItem(
                tipo = MultimediaTipo.IMAGEN,
                descripcion = context.getString(R.string.imagen_de_ejemplo),
                uri = "file://example.jpg"
            )
            onMediaSelected(dummyMedia)
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(stringResource(R.string.seleccionar_multimedia))
    }
}