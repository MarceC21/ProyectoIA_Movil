package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.proyecto.data.model.AnimalInfo
import com.example.proyecto.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: HomeViewModel
) {
    // Obtener JSON de args
    val json = backStackEntry.arguments?.getString("animalJson") ?: ""
    val animalInfo = viewModel.jsonToAnimalInfo(json)

    if (animalInfo == null) {
        // Fallback error
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error al cargar información del animal.")
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título del animal
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = animalInfo.commonName,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Nombre científico: ${animalInfo.scientificName}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Secciones
        InfoSection("Hábitat", animalInfo.habitat)
        InfoSection("Alimentación", animalInfo.diet)
        InfoSection("Curiosidad", animalInfo.curiosity)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Volver a Inicio")
        }
    }
}

@Composable
private fun InfoSection(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}