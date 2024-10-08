package com.example.proyectofinal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.TareasNotasViewModel
import com.example.proyectofinal.ui.PrincipalLayout
import com.example.proyectofinal.ui.Agregar
import com.example.proyectofinal.ui.BotonFlotante
import com.example.proyectofinal.ui.ItemLayout
import com.example.proyectofinal.ui.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val tareasNotasViewModel: TareasNotasViewModel = viewModel() // Instancia compartida del ViewModel

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tareas", "Notas")

    NavHost(
        navController = navController,
        startDestination = "principal"
    ) {
        composable("principal") {
            PrincipalLayout(navController, tareasNotasViewModel, tabs, selectedTabIndex) {
                selectedTabIndex = it
            }
        }
        composable("agregar") {
            Agregar(navController, tareasNotasViewModel)
        }
        composable("item/{itemTitulo}") { backStackEntry ->
            val itemTitulo = backStackEntry.arguments?.getString("itemTitulo") ?: ""
            ItemLayout(navController, tareasNotasViewModel, itemTitulo)
        }
    }
}