@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class Tarea : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TareaLayout()
                }
            }
        }
    }
}

@Composable
fun TareaLayout()
{
    Scaffold(
        topBar = { TopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(
                    modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = "Titulo de la tarea/Nota",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = "Fecha de creación/Vencimiento",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    ))
                {
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Descripción de la tarea/Nota") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(100.dp),
                        enabled = false
                    )
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    ))
                {
                    Text(
                        text = "Multimedia",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                    Text(
                        text = "Archivos multimedia",
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Text(
                        text = "Archivos multimedia",
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

            }
        }
    }


}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Acción */ }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                }
                Text("Itsur Notes")
            }
        },
        actions = {
            IconButton(onClick = { /* Acción */ }) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
            }
            IconButton(onClick = { /* Acción */ }) {
                Icon(Icons.Filled.Edit, contentDescription = "Editar")
            }

        }

    )
}


@Preview(showBackground = true)
@Composable
fun TareaPreview() {
    ProyectoFinalTheme {
        TareaLayout()
    }
}