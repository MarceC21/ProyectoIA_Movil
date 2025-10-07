package com.example.proyecto.domain.usecase

import com.example.proyecto.data.model.ChatResponse
import com.example.proyecto.data.repository.OpenAiRepository

class GetChatResponseUseCase(private val repository: OpenAiRepository) {
    suspend operator fun invoke(prompt: String): ChatResponse? {
        return repository.getChatResponse(prompt)
    }
}