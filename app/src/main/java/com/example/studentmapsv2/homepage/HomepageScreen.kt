package com.example.studentmapsv2.homepage

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studentmapsv2.NavigationItem
import com.example.studentmapsv2.ui.theme.AccentColor
import com.example.studentmapsv2.ui.theme.HighlightColor
import com.example.studentmapsv2.ui.theme.MainColor

@Composable
fun HomepageScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTitle(
            modifier = Modifier.padding(40.dp),
            text = "StudentMapps",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        LazyColumn {
            itemsIndexed(items = listOf(locationFilterItems)) { index, locations ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AccentColor)
                        .padding(top = 10.dp)
                )
                {
                    Column {
                        FilterName(filters = locations, filterIndex = index)
                        LocationFilterButtons(viewModel = viewModel, filters = locations)
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainColor)
                        .padding(10.dp)
                )
            }
        }

        LazyColumn {
            itemsIndexed(items = allFilters) { i, filters ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AccentColor)
                        .padding(top = 10.dp)
                )
                {
                    Column {
                        FilterName(filters = filters, filterIndex = i)
                        FilterButtons(viewModel = viewModel, filters = filters)
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainColor)
                        .padding(10.dp)
                )
            }
        }
        SearchButton(navController)
    }
}

@Composable
fun AppTitle(
    modifier: Modifier,
    text: String,
    style: TextStyle
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}

@Composable
fun FilterName(
    filters: List<FilterItem>,
    filterIndex: Int
) {
    Text(
        text = filters[filterIndex].type,
        modifier = Modifier
            .padding(start = 5.dp),
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = HighlightColor
        )
    )
}

@Composable
fun FilterButtons(
    viewModel: HomeViewModel,
    filters: List<FilterItem>
) {
    LazyRow {
        item {
            filters.forEach {
                var selected by remember { mutableStateOf(false) }
                val color = if (selected) AccentColor else HighlightColor
                val border = if (selected) BorderStroke(2.dp, HighlightColor) else BorderStroke(
                    0.dp,
                    HighlightColor
                )
                Button(
                    onClick = {
                        selected = !selected
                        if (selected) {
                            viewModel.query.add(it.filter)
                            it.isSelected = true
                        } else {
                            viewModel.query.remove(it.filter)
                            it.isSelected = false
                        }
                        Log.d("<<<<<", viewModel.query.toString())
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    shape = RoundedCornerShape(15),
                    border = border,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Text(
                        it.filter
                    )
                }
            }
        }
    }
}

@Composable
fun LocationFilterButtons(
    viewModel: HomeViewModel,
    filters: List<FilterItem>
) {
    LazyRow {
        item {
            filters.forEach { item ->
                var selected by remember { mutableStateOf(false) }
                val color = if (selected) AccentColor else HighlightColor
                val border = if (selected) BorderStroke(2.dp, HighlightColor) else BorderStroke(
                    0.dp,
                    HighlightColor
                )
                val containsLocations = locationFiltersList.any { viewModel.query.contains(it) }
                Button(
                    onClick = {
                        if (containsLocations) {
                            if (viewModel.query.contains(item.filter)) {
                                selected = !selected
                                addItemToQuery(viewModel, selected, item)
                            }
                        } else {
                            selected = !selected
                            addItemToQuery(viewModel, selected, item)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    shape = RoundedCornerShape(15),
                    border = border,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Text(
                        item.filter
                    )
                }
            }
        }
    }
}

@Composable
fun SearchButton(
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(NavigationItem.Map.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = HighlightColor),
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
        ) {
            Text(text = "Search")
        }
    }
}

fun addItemToQuery(
    viewModel: HomeViewModel,
    selected: Boolean,
    item: FilterItem
) {
    if (selected) {
        viewModel.query.add(item.filter)
    } else {
        viewModel.query.remove(item.filter)
    }
    Log.d("<<<<<", viewModel.query.toString())
}

/*@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomepageScreen(navController = nav)
}*/
