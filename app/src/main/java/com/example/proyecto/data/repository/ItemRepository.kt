package com.example.proyecto.data.repository

import com.example.proyecto.data.model.Item
import com.example.proyecto.data.remote.MockApi


interface ItemRepository {
    suspend fun getItems(query: String? = null): List<Item>
}


class ItemRepositoryImpl(private val api: MockApi) : ItemRepository {
    override suspend fun getItems(query: String?): List<Item> = api.fetchItems(query)
}