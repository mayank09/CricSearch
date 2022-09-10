package com.cricbuzz.cricsearch.ui.components

import android.content.Context
import android.util.Log
import com.cricbuzz.cricsearch.R
import com.cricbuzz.cricsearch.model.menus.Menu
import com.cricbuzz.cricsearch.model.menus.MenuResponse
import com.cricbuzz.cricsearch.model.restaurants.Restaurant
import com.cricbuzz.cricsearch.model.restaurants.RestaurantResponse
import com.cricbuzz.cricsearch.util.Resource
import com.cricbuzz.cricsearch.util.filterData
import com.cricbuzz.cricsearch.util.getResponseFromAsset
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

const val RESTAURANT_SOURCE = "Restaurants.json"
const val MENU_SOURCE = "Menus.json"

@ViewModelScoped
class MainRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getRestaurants(query: String?): Resource<Map<Restaurant, Menu?>> {

        return try {
            //Initial state.....
            Resource.Loading<Any>()

            //get list of restaurants
            val restaurants =
                context.getResponseFromAsset<RestaurantResponse>(RESTAURANT_SOURCE).data?.restaurants
            Log.d("restaurants", restaurants.toString())

            //get list of menu
            val menus = context.getResponseFromAsset<MenuResponse>(MENU_SOURCE).data?.menus
            Log.d("menus", menus.toString())

            //get map<restId, menus>
            val menusByRestaurantId = menus?.associateBy { it.restaurantId }
            Log.d("Map of <resId, menus>", menusByRestaurantId.toString())

            //associate menus with restaurants based on resId: Map<Restaurant, Menus>
            var restaurantsWithMenus =
                restaurants?.associateWith { restaurant -> menusByRestaurantId?.get(restaurant.id) }
            Log.d("Combined Map", restaurantsWithMenus.toString())


            if (!query.isNullOrEmpty()) {
                // filter data based on search keyword
                restaurantsWithMenus = restaurantsWithMenus.filterData(query)
            }

            if (restaurantsWithMenus.isNullOrEmpty())
                Resource.Error(message = context.getString(R.string.no_result_found))
            else
                Resource.Success(data = restaurantsWithMenus)

        } catch (ex: Exception) {
            ex.printStackTrace()
            Resource.Error(message = ex.message!!)
        }
    }
}