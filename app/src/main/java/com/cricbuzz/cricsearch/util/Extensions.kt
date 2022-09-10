package com.cricbuzz.cricsearch.util

import android.content.Context
import android.util.Log
import com.cricbuzz.cricsearch.model.menus.Menu
import com.cricbuzz.cricsearch.model.restaurants.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

enum class SortingOrder {
    ITEMS,
    CUISINE,
    RESTAURANT
}

inline fun <reified T> Context.getResponseFromAsset(fileName: String): Resource<T?> {

    val jsonString: String

    try {
        jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return Resource.Error(message = ioException.message!!)
    }

    Log.d("response", jsonString)

    val responseType = object : TypeToken<T>() {}.type
    return Resource.Success(data = Gson().fromJson(jsonString, responseType))
}


fun Map<Restaurant, Menu?>?.filterData(query: String): MutableMap<Restaurant, Menu> {
    val filteredMap = mutableMapOf<Restaurant, Menu>()

    this?.forEach { (restaurant, menu) ->

        //get all menu items in all categories for a restaurant
        val items = menu?.categories?.flatMap { category -> category.menuItems.map { it.name } }

        //find menu items for searched keyword
        val filteredItem = items?.firstOrNull{ it.contains(query, true) }

        //if found a item match,
        if (filteredItem != null) {
            //add matching restaurant to filtered Map with sorting order as item
            restaurant.sortingOrder = SortingOrder.ITEMS.ordinal
            menu.let {
                filteredMap[restaurant] = it
            }
        }

        //if found a cuisine match,
        else if (restaurant.cuisine_type.contains(query, true)) {
            //add matching restaurant to filtered Map with sorting order as cuisine
            restaurant.sortingOrder = SortingOrder.CUISINE.ordinal
            menu?.let {
                filteredMap[restaurant] = it
            }
        }

        //if found a restaurant match
        else if (restaurant.name.contains(query, true)) {
            //add matching restaurant to filtered Map with sorting order as restaurant
            restaurant.sortingOrder = SortingOrder.RESTAURANT.ordinal
            menu?.let {
                filteredMap[restaurant] = it
            }
        }
    }
    return filteredMap
}



