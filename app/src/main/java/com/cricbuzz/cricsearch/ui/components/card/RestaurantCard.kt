package com.cricbuzz.cricsearch.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cricbuzz.cricsearch.NavRoutes
import com.cricbuzz.cricsearch.model.restaurants.Restaurant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Restaurant.RestaurantCard(navController: NavController) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
        backgroundColor = Color.LightGray,
        onClick = {
            navController.navigate(NavRoutes.DetailScreen.route + "/${this.name}")
        }
    ) {
        Row {
            Image(
                imageVector = Icons.Default.AccountBox,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = this@RestaurantCard.name,
                    fontWeight = FontWeight.Bold
                )
                Text(text = this@RestaurantCard.cuisine_type)
                Row(Modifier.padding(top = 8.dp)) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    Text(text = this@RestaurantCard.neighborhood)
                }
            }
        }
    }
}