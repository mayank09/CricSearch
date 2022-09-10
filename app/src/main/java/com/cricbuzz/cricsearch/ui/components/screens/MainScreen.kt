package com.cricbuzz.cricsearch.ui.components.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cricbuzz.cricsearch.ui.components.MainViewModel
import com.cricbuzz.cricsearch.ui.components.card.RestaurantCard


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = viewModel.restaurantState.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {

            OutlinedTextField(
                value = query.value,
                onValueChange = {
                    query.value = it
                    viewModel.getRestaurantList(query.value)

                },
                enabled = true,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (query.value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                // Remove text from TextField when you press the 'X' icon
                                query.value = ""
                                viewModel.getRestaurantList()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }


                },
                label = { Text(text = "Search here...") },
                modifier = Modifier.fillMaxWidth(),

            )

            if (result.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (result.error.isNotBlank()) {
                Log.d("TAG", "MainContent: ${result.error}")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = viewModel.restaurantState.value.error
                    )
                }
            }

            if (result.data.isNotEmpty()) {
                LazyColumn(Modifier.padding(top = 8.dp)) {
                    result.data.keys.toList().sorted()
                        .let {
                            items(it) { restaurant ->
                                restaurant.RestaurantCard(navController)
                            }
                        }
                }
            }
        }
    }
}