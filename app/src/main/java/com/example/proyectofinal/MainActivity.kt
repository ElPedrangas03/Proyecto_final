package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

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
    // Boton flotante
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
        // Nueva fila para elementos clicables en la parte superior
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
        // Cada tarea individual
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
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
                    text = "Titulo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fecha",
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProyectoFinalPreview() {
    ProyectoFinalTheme {
        PrincipalLayout()
    }
}