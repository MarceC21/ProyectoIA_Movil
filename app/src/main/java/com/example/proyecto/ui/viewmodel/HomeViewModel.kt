package com.example.proyecto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.model.ChatResponse
import com.example.proyecto.data.repository.OpenAiRepository
import com.example.proyecto.domain.usecase.GetChatResponseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// MVI: Estados de la UI
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val response: String) : UiState()
    data class Error(val message: String) : UiState()
}

// MVI: Intents (acciones del usuario)
sealed class UiIntent {
    data class SendPrompt(val prompt: String) : UiIntent()
}

class HomeViewModel : ViewModel() {
    private val repository = OpenAiRepository()
    private val useCase = GetChatResponseUseCase(repository)

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun processIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.SendPrompt -> sendPrompt(intent.prompt)
        }
    }

    private fun sendPrompt(prompt: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response: ChatResponse? = useCase(prompt)
            _uiState.value = if (response != null && response.choices.isNotEmpty()) {
                val content = response.choices.first().message.content
                UiState.Success(content)
            } else {
                UiState.Error("Error al obtener respuesta de OpenAI. Verifica tu clave API.")
            }
        }
    }
}