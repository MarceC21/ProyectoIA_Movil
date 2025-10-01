package com.example.proyecto.data.remote

import com.example.proyecto.data.model.Item
import kotlinx.coroutines.delay


/** Simula un endpoint remoto que devuelve una lista de items. */
class MockApi {
    private val mock = listOf(
        Item("1","Manzana","Fruta roja"),
        Item("2","Plátano","Fruta amarilla"),
        Item("3","Cereza","Fruta pequeña")
    )


    suspend fun fetchItems(query: String? = null): List<Item> {
// Simula latencia de red
        delay(700)
        return if (query.isNullOrBlank()) mock
        else mock.filter { it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true) }
    }
}