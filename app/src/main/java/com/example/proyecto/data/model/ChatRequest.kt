package com.example.proyecto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    @Json(name = "max_tokens") val maxTokens: Int = 150  // Opcional
)

@JsonClass(generateAdapter = true)
data class Message(
    val role: String,  // "user"
    val content: String  // El prompt
)
