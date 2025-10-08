package com.example.proyecto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.model.AnimalInfo
import com.example.proyecto.data.model.ChatResponse
import com.example.proyecto.data.repository.OpenAiRepository
import com.example.proyecto.domain.usecase.GetChatResponseUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// MVI: Estados de la UI (internos para no crear archivos extras)
sealed class UiState {
    object Idle : UiState()  // Inicial
    object Loading : UiState()  // Cargando
    data class Success(val animalInfo: AnimalInfo) : UiState()  // Info lista para mostrar/navegar
    data class Error(val message: String) : UiState()  // Error
}

// MVI: Intents (internos)
sealed class UiIntent {
    data class SendAnimal(val animalName: String) : UiIntent()
    object GetRandomAnimal : UiIntent()
}

class HomeViewModel : ViewModel() {
    private val repository = OpenAiRepository()
    private val useCase = GetChatResponseUseCase(repository)

    // Moshi para parsear/serializar JSON
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val animalInfoAdapter = moshi.adapter(AnimalInfo::class.java)

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun processIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.SendAnimal -> sendAnimalInfo(intent.animalName)
            is UiIntent.GetRandomAnimal -> getRandomAnimal()
        }
    }

    // Función para obtener JSON de AnimalInfo (para Nav args)
    fun animalInfoToJson(animalInfo: AnimalInfo): String {
        return animalInfoAdapter.toJson(animalInfo) ?: ""
    }

    // Función para parsear JSON a AnimalInfo (para ResultScreen)
    fun jsonToAnimalInfo(json: String): AnimalInfo? {
        return try {
            animalInfoAdapter.fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // NUEVO: Método para resetear estado (llamado desde UI después de navegar)
    fun resetState() {
        _uiState.value = UiState.Idle
    }

    private fun sendAnimalInfo(animalName: String) {
        if (animalName.isBlank()) return

        val prompt = "Proporciona información sobre el animal '$animalName'. Responde **solo** en formato JSON con esta estructura exacta: { \"commonName\": \"nombre común\", \"scientificName\": \"nombre científico\", \"habitat\": \"hábitat\", \"diet\": \"alimentación\", \"curiosity\": \"una curiosidad interesante\" }. Usa español para todo. Si no conoces el animal, usa uno similar."

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val responseState = processPrompt(prompt)
            _uiState.value = responseState
        }
    }

    private fun getRandomAnimal() {
        val prompt = "Elige un animal aleatorio (mamífero, ave, reptil, pez, etc.) y proporciona su información. Responde **solo** en formato JSON con esta estructura exacta: { \"commonName\": \"nombre común\", \"scientificName\": \"nombre científico\", \"habitat\": \"hábitat\", \"diet\": \"alimentación\", \"curiosity\": \"una curiosidad interesante\" }. Usa español para todo."

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val responseState = processPrompt(prompt)
            _uiState.value = responseState
        }
    }

    private suspend fun processPrompt(prompt: String): UiState {
        val chatResponse: ChatResponse? = useCase(prompt)
        return if (chatResponse != null && chatResponse.choices.isNotEmpty()) {
            val content = chatResponse.choices.first().message.content.trim()
            try {
                val animalInfo = animalInfoAdapter.fromJson(content)
                if (animalInfo != null) {
                    UiState.Success(animalInfo)
                } else {
                    UiState.Error("Respuesta inválida de OpenAI.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                UiState.Error("Error al procesar la respuesta. Intenta de nuevo.")
            }
        } else {
            UiState.Error("Error al obtener respuesta de OpenAI. Verifica tu clave API o conexión.")
        }
    }
}