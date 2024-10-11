package com.example.proyectofinal.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.proyectofinal.R


//@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
//@Composable
//fun CollectionGalleryView() {
//    val context = LocalContext.current
//    var imagesUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
//
//    val multiplePhoto = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
//    ) { uris ->
//        imagesUris = uris
//    }
//
//    Column(
//        modifier = Modifier
//            //.padding(16.dp)
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Button(onClick = {
//            multiplePhoto.launch(
//                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//            )
//        }) {
//            Text("Seleccionar archivo")
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CollectionGalleryView(imagesUris: List<Uri>, onImagesChanged: (List<Uri>) -> Unit) {
    val context = LocalContext.current

    val multiplePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
    ) { uris ->
        onImagesChanged(uris)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = {
            multiplePhoto.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text("Seleccionar archivo")
        }
    }
}