package com.example.proyectofinal.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal.ProyectoFinalApplication

object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        initializer {
            TareasNotasViewModel(
                tareaRepository = proyectoFinalApplication().container.tareaRepository,
                notaRepository = proyectoFinalApplication().container.notaRepository
            )
        }
    }
}

fun CreationExtras.proyectoFinalApplication(): ProyectoFinalApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ProyectoFinalApplication)