package com.cricbuzz.cricsearch.ui.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricbuzz.cricsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _restaurantState: MutableState<MainState> = mutableStateOf(MainState())
    val restaurantState: State<MainState> = _restaurantState

    init {
        getRestaurantList()
    }

    fun getRestaurantList(query: String? = null) = viewModelScope.launch {
        _restaurantState.value = MainState(isLoading = true)

        try {
            val response = repository.getRestaurants(query)

            if (response is Resource.Success) {
                response.data?.let {
                    _restaurantState.value = MainState(data = it)
                }

            } else if (response is Resource.Error) {
                response.message?.let {
                    _restaurantState.value = MainState(error = it)
                }
            }
        } catch (ex: Exception) {
            _restaurantState.value = MainState(error = ex.message.toString())
        }
    }
}