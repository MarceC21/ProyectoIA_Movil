package com.example.proyecto.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.model.Item
import com.example.proyecto.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class HomeIntent {
    data class Search(val query: String) : HomeIntent()
    object Load : HomeIntent()
}


data class HomeState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val query: String = "",
    val error: String? = null
)


class HomeViewModel(private val getItems: GetItemsUseCase) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state


    init {
        handleIntent(HomeIntent.Load)
    }


    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Load -> loadItems()
            is HomeIntent.Search -> search(intent.query)
        }
    }


    private fun loadItems() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val items = getItems()
                _state.value = _state.value.copy(isLoading = false, items = items)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }


    private fun search(query: String) {
        _state.value = _state.value.copy(isLoading = true, query = query, error = null)
        viewModelScope.launch {
            try {
                val items = getItems(query)
                _state.value = _state.value.copy(isLoading = false, items = items)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}