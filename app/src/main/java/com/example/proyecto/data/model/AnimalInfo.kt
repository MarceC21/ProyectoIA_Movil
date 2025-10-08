package com.example.proyecto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimalInfo(
    @Json(name = "commonName") val commonName: String,
    @Json(name = "scientificName") val scientificName: String,
    @Json(name = "habitat") val habitat: String,
    @Json(name = "diet") val diet: String,
    @Json(name = "curiosity") val curiosity: String
)
