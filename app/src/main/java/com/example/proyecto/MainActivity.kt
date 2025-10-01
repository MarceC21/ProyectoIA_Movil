package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto.data.remote.MockApi
import com.example.proyecto.data.repository.ItemRepositoryImpl
import com.example.proyecto.domain.usecase.GetItemsUseCase
import com.example.proyecto.ui.screens.HomeScreen
import com.example.proyecto.ui.theme.ProyectoTheme
import com.example.proyecto.ui.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Wiring manual (sin Hilt todavía)
        val api = MockApi()
        val repo = ItemRepositoryImpl(api)
        val useCase = GetItemsUseCase(repo)
        val viewModel = HomeViewModel(useCase)

        setContent {
            ProyectoTheme {
                HomeScreen(viewModel = viewModel) { item ->
                    // Acción al hacer click en un item
                    // por ejemplo mostrar un Toast o navegar
                }
            }
        }
    }
}
