package com.example.proyectofinal.ui

import android.content.Context
import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.Date
import java.io.File
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R


//@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
//@Composable
//fun CameraView() {
//    val context = LocalContext.current
//    var imagesUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
//
//    val file = context.createImageFile()
//    val uri = FileProvider.getUriForFile(
//        context,
//        "${context.packageName}.provider", file
//    )
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture()
//    ) {
//        if (it) imagesUris = imagesUris + uri
//    }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { granted ->
//        if (granted) {
//            cameraLauncher.launch(uri)
//        } else {
//            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
//
//    Column(
//        modifier = Modifier
//            //.padding(16.dp)
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Button(onClick = {
//            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//                cameraLauncher.launch(uri)
//            } else {
//                permissionLauncher.launch(android.Manifest.permission.CAMERA)
//            }
//        }) {
//            Text("Tomar foto")
//        }
//
//        FlowRow(
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            imagesUris.forEach { uri ->
//                AsyncImage(
//                    model = ImageRequest.Builder(context).data(uri)
//                        .crossfade(enable = true).build(),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(120.dp)
//                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
//                )
//            }
//        }
//    }
//}
//
//@SuppressLint("SimpleDateFormat")
//fun Context.createImageFile(): File {
//    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//    val imageFileName = "JPEG_$timeStamp"
//    return File.createTempFile(
//        imageFileName,
//        ".jpg",
//        externalCacheDir
//    )
//}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CameraView(imagesUris: List<Uri>, onImagesChanged: (List<Uri>) -> Unit) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider", file
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) onImagesChanged(listOf(uri)) // Añadir la nueva imagen a la lista
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = {
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }) {
            Text("Tomar foto")
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_$timeStamp"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}