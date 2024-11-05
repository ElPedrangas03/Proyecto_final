package com.example.proyectofinal

import android.app.Application
import com.example.proyectofinal.data.AppContainer
import com.example.proyectofinal.data.AppDataContainer

class ProyectoFinalApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}