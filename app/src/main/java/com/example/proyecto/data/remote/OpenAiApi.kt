package com.example.proyecto.data.remote

import com.example.proyecto.data.model.ChatRequest
import com.example.proyecto.data.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApi {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")  // Endpoint estándar de OpenAI; cambia a /v1/responses si es API custom
    suspend fun getChatResponse(
        @Body request: ChatRequest
    ): ChatResponse
}