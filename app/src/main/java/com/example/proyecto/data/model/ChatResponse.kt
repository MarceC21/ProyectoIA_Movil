package com.example.proyecto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatResponse(
    val choices: List<Choice>
)

@JsonClass(generateAdapter = true)
data class Choice(
    val message: MessageResponse
)

@JsonClass(generateAdapter = true)
data class MessageResponse(
    val role: String,
    val content: String  // La respuesta del AI
)
