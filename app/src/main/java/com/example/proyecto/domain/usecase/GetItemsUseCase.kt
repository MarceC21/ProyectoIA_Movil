package com.example.proyecto.domain.usecase

import com.example.proyecto.data.model.Item
import com.example.proyecto.data.repository.ItemRepository


class GetItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(query: String? = null): List<Item> {
        return repository.getItems(query)
    }
}