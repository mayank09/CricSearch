package com.cricbuzz.cricsearch.model.menus

import com.google.gson.annotations.SerializedName

data class Category(
    val id: String,
    @SerializedName("menu-items")
    val menuItems: List<MenuItem>,
    val name: String
)