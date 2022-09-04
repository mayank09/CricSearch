package com.cricbuzz.cricsearch.ui.components

import com.cricbuzz.cricsearch.model.menus.Menu
import com.cricbuzz.cricsearch.model.restaurants.Restaurant

data class MainState(
    val isLoading:Boolean=false,
    val data:Map<Restaurant, Menu?> = emptyMap(),
    val error:String=""
)