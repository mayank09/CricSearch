package com.cricbuzz.cricsearch.model.restaurants

data class Review(
    val comments: String,
    val date: String,
    val name: String,
    val rating: Int
)