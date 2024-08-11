package dev.sagar.playinfo.domain

data class GameDetail(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Double,
    val released: String,
)
