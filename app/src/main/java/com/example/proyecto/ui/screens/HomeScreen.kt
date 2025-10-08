package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.proyecto.ui.viewmodel.HomeViewModel
import com.example.proyecto.ui.viewmodel.UiIntent
import com.example.proyecto.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var animalInput by remember { mutableStateOf("") }

    // NUEVO: LaunchedEffect para detectar Success y navegar UNA VEZ
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            val json = viewModel.animalInfoToJson((uiState as UiState.Success).animalInfo)
            navController.navigate("result/$json")
            viewModel.resetState()  // Reset después de navegar (usa el método del ViewModel)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "MiniZoológico",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Input
        OutlinedTextField(
            value = animalInput,
            onValueChange = { animalInput = it },
            label = { Text("Nombre del animal (ej: león)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Dos botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.processIntent(UiIntent.SendAnimal(animalInput))
                    animalInput = ""
                },
                modifier = Modifier.weight(1f),
                enabled = animalInput.isNotBlank()
            ) {
                Text("Buscar Animal")
            }
            Button(
                onClick = { viewModel.processIntent(UiIntent.GetRandomAnimal) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Animal Aleatorio")
            }
        }

        // Bottom nav para About (opcional, ajusta si no lo quieres)
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Info, contentDescription = null) },  // Importa Icons
                label = { Text("About") },
                selected = false,
                onClick = { navController.navigate("about") }
            )
        }

        // Manejar Loading y Error (Success se maneja en LaunchedEffect)
        when (uiState) {
            is UiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Buscando...")
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Error: ${(uiState as UiState.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else -> {
                // Idle: Mensaje opcional
                Text(
                    text = "Busca un animal o elige uno aleatorio para aprender sobre él.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}