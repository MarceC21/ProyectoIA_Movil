package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto.data.model.Item
import com.example.proyecto.ui.components.ItemCard
import com.example.proyecto.ui.viewmodel.HomeIntent
import com.example.proyecto.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClick: (Item) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MVI Compose Template") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.handleIntent(HomeIntent.Load) }
            ) {
                Text("↻")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Input
            var text by remember { mutableStateOf(state.query) }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.handleIntent(HomeIntent.Search(text)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn {
                        items(state.items) { item ->
                            ItemCard(item = item, onClick = onItemClick)
                        }
                    }
                }
            }
        }
    }
}
