@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyectofinal



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class Formulario : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Agregar()
                }
            }
        }
    }
}

@Composable
fun Agregar()
{
    Scaffold(
        topBar = { Bar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            )
            {
                var title by remember { mutableStateOf("") }
                var dueDate by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var selectedFiles by remember { mutableStateOf(listOf<String>()) } // Lista de archivos seleccionados
                var expanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    //Nota o Tarea
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                    ) {
                        SelectItem()
                    }
                    //Titulo
                    Row(
                        modifier = Modifier
                        .padding(start = 5.dp)
                        .fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Titulo de la Tarea",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth())
                    {
                        TextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Título") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    //Fecha de vencimiento
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Fecha de vencimiento",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )

                    }
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth())
                    {
                        TextField(
                            value = dueDate,
                            onValueChange = { dueDate = it },
                            label = { Text("Fecha de Vencimiento") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    //Descripción
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                    {
                        TextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descripción") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )

                    }
                    //Archivos multimedia
                    // Selección de archivos multimedia
                    Text("Archivos multimedia seleccionados: ${selectedFiles.joinToString(", ")}")

                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth() )
                    {
                        Button(onClick = {
                            //selectedFiles = selectedFiles + "archivo.txt"
                        }) {
                            Text("Seleccionar Archivos")
                        }
                    }
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center)
                    {
                        // Botón para enviar el formulario
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Enviar")
                        }
                    }

                }

            }
        }
    }


}


@Composable
fun SelectItem() {
    var selectedOption by remember { mutableStateOf("Nota") }

    Column(modifier = Modifier.padding(5.dp)) {
        Text("Selecciona una opción:",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,)

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 20.dp)
            )
            {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {  RadioButton(
                    selected = selectedOption == "Nota",
                    onClick = { selectedOption = "Nota" }
                )
                    Text("Nota", modifier = Modifier.clickable { selectedOption = "Nota" })}

            }
            Column(
                modifier = Modifier
                    .width(100.dp)
            )
            {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                { RadioButton(
                    selected = selectedOption == "Tarea",
                    onClick = { selectedOption = "Tarea" }
                )
                    Text("Tarea", modifier = Modifier.clickable { selectedOption = "Tarea" }) } }


        }

    }
}


@Composable
fun Bar() {
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
        }

    )
}


@Preview(showBackground = true)
@Composable
fun AgregarPreview() {
    ProyectoFinalTheme {
        Agregar()
    }
}