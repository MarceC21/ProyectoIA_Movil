package com.example.proyecto.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
//import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto.data.model.Item


@Composable
fun ItemCard(item: Item, onClick: (Item) -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onClick(item) }) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = item.title)
            Text(text = item.description)
        }
    }
}