package com.example.studentmapsv2.homepage

data class FilterItem(
    val type: String,
    val filter: String,
    var isSelected: Boolean = false
)

val restaurantFiltersList = listOf("Cheap", "Delivers", "Discounts")
val locationFiltersList = listOf("London", "Brighton", "Manchester", "Cambridge", "Bristol", "Nottingham")
val pubFiltersList = listOf("Themes", "Cheap", "Discounts")

val locationFilterItems = locationFiltersList.map {
    FilterItem(
        type = "Location",
        filter = it,
    )
}
val restaurantFilterItems = restaurantFiltersList.map {
    FilterItem(
        type = "Restaurant",
        filter = it,
    )
}
val pubFilterItems = pubFiltersList.map {
    FilterItem(
        type = "Pub",
        filter = it,
    )
}

val allFilters = listOf(restaurantFilterItems, pubFilterItems)


