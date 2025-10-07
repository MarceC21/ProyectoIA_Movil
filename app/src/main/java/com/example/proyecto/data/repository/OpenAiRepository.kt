package com.example.proyecto.data.repository

import com.example.proyecto.BuildConfig
import com.example.proyecto.data.model.ChatRequest
import com.example.proyecto.data.model.ChatResponse
import com.example.proyecto.data.model.Message
import com.example.proyecto.data.remote.ModelConfig
import com.example.proyecto.data.remote.OpenAiApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class OpenAiRepository {
    private val api: OpenAiApi

    init {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())  // Para soporte Kotlin en Moshi
                        .build()
                )
            )
            .build()

        api = retrofit.create(OpenAiApi::class.java)
    }

    suspend fun getChatResponse(prompt: String): ChatResponse? {
        return try {
            val request = ChatRequest(
                model = ModelConfig.DEFAULT_MODEL,
                messages = listOf(Message("user", prompt))
            )
            api.getChatResponse(request)
        } catch (e: Exception) {
            e.printStackTrace()  // Log para debug; maneja en ViewModel
            null
        }
    }
}