package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    PrincipalLayout()
                }
            }
        }
    }
}

@Composable
fun PrincipalLayout(){
    // Variable para tomar lo que tengamos en el campo de buscar
    var buscarText by remember { mutableStateOf("") }

    // Boton flotante para mandar a la otra pestaña
    // Para que cree una nueva tarea
    Box(modifier = Modifier.fillMaxWidth())
    {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .padding(60.dp)
                .align(Alignment.BottomEnd)
                .size(70.dp)
        ) {
            Text("Botón")
        }
    }
    // Listas de tareas (Es esqueleto por ahora xd)
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    )
    {
        TextField(
            value = buscarText,
            onValueChange = { buscarText = it },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        // Nueva fila para elementos clicables en la parte superior, para notas y tareas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Notas",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .padding(end = 5.dp)
                    .clickable {}
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    )

            )
            Text(
                text = "Tareas",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {}
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    )
            )
        }
        // Nueva fila para elementos clicables en la parte superior para pendientes y realizadas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pendientes",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 5.dp)
                    .clickable {}
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    )

            )
            Text(
                text = "Realizadas",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .border(
                        BorderStroke(2.dp, Color.Black)
                    )
            )
        }
        BoxTarea("Tarea 1", "Terminar el diseño")
        BoxTarea("Tarea 2", "Jugar fornais")
    }
}

@Composable
fun BoxTarea(
    Titulo : String,
    Fecha : String // Cambiarlo despues por el parametro del DatePicker o algo asi
)
{
    // Cada tarea individual
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(
                BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp)
            )
    )
    {
        Column(modifier = Modifier.padding(20.dp))
        {
            Text(
                text = Titulo,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = Fecha,
                fontWeight = FontWeight.Light
            )
        }
        Row(modifier = Modifier
                .padding(20.dp)
        )
        {
            // Boton para cancelar o borrar la tarea
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
            )
            {

            }
            // Boton para terminar la tarea (Algo como para marcar que esta listo)
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
            )
            {

            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProyectoFinalPreview() {
    ProyectoFinalTheme {
        PrincipalLayout()
    }
}