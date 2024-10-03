package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    // Boton flotante, por ahora solo estorba
    /*Box(modifier = Modifier.fillMaxWidth())
    {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .padding(60.dp)
                .align(Alignment.BottomEnd)
                .size(80.dp)
        ) {
            Text("Más multimedia")
        }
    }*/
    Column(modifier = Modifier.fillMaxWidth())
    {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(2.dp, Color.Black)
            ))
        {
            Text(
                text = "Titulo de la tarea",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(2.dp, Color.Black)
            ))
        {
            Text(
                text = "Fecha de creación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
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
                label = { Text("Escribe aqui tu descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(100.dp)
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
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .size(100.dp)
            )
            {
                Text(text = "Nuevo")
            }
        }
        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(2.dp, Color.Black)
            ))
        {
            Text(
                text = "Clasificación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp)
            )
            // Agregar un elemento en el que se pueda
            // clasificar lo que estamos haciendo
            // cambiandolo a nota o tarea segun querramos
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaPreview() {
    ProyectoFinalTheme {
        TareaLayout()
    }
}