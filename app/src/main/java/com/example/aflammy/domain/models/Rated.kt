package com.example.aflammy.domain.models

data class Rated(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val releaseDate: String,
    var mediaType:String
)
