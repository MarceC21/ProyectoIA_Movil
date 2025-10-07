package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel  // Para viewModel() en Compose
import com.example.proyecto.ui.viewmodel.HomeViewModel
import com.example.proyecto.ui.viewmodel.UiIntent
import com.example.proyecto.ui.viewmodel.UiState
import androidx.compose.runtime.collectAsState  // IMPORT CORRECTO: De Compose runtime, no coroutines

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()  // Se resuelve con el import y dependencia
) {
    var prompt by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()  // Ahora se resuelve correctamente (línea ~18)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Chat con OpenAI",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("Ingresa tu prompt") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (prompt.isNotBlank()) {
                    viewModel.processIntent(UiIntent.SendPrompt(prompt))
                    prompt = ""  // Limpia el campo
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = prompt.isNotBlank()
        ) {
            Text("Enviar a OpenAI")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is UiState.Idle -> {
                Text(
                    text = "Ingresa un prompt y presiona enviar para chatear con IA.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text("Generando respuesta...")
            }
            is UiState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Respuesta de OpenAI:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (uiState as UiState.Success).response,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Error: ${(uiState as UiState.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
