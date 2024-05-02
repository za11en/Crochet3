package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.example.crochet3.ui.theme.AppPrime
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.shadow
import com.example.crochet3.viewModels.FirestoreViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(navController: NavController, firestoreViewModel: FirestoreViewModel = viewModel()){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel: FirestoreViewModel = viewModel()
    val dataState = viewModel.getCollection("PatternDatabase").observeAsState(initial = null)

    var searchText by remember { mutableStateOf("")}
    var active by remember {mutableStateOf(true)}
    var items = remember { mutableStateListOf(
        "Baby Blanket",
        "Halloween"
    )
    }
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "Search", drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient())
        ) {
            Column(modifier = Modifier.padding(top = 100.dp)) {
               
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    query = searchText,
                    onQueryChange = {searchText = it},
                    onSearch = {active = false },
                    active = active ,
                    onActiveChange = {active = it},
                    placeholder = { Text("Search Patterns") },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.White,
                        dividerColor = AppPrime,
                        inputFieldColors = TextFieldDefaults.colors(
                            focusedTextColor = Color.DarkGray,
                            unfocusedTextColor = Color.DarkGray,)),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)) },
                    trailingIcon = {
                        if(active) {
                            Row(){
                            Icon(
                                painter = painterResource(id = R.drawable.clear),
                                contentDescription = "clear search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { if (searchText.isNotEmpty()) { searchText = ""} else { active = false } })
                                Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.voice),
                                contentDescription = "voice search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { /*TODO*/ }
                            )
                                Spacer(modifier = Modifier.width(8.dp))
                                }
                        } else {
                        Icon(
                            painter = painterResource(id = R.drawable.voice),
                            contentDescription = "voice search",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { /*TODO*/ }
                        ) }
                    }
                ) {
                    Text("Recent", color = AppPrime, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp, top = 16.dp))
                    items.forEach {
                        Row(modifier = Modifier.padding(all = 16.dp )){
                            Icon(modifier = Modifier.padding(end = 8.dp),imageVector = Icons.Filled.Star, contentDescription = "", tint = Color.DarkGray)
                            Text(text = it, color = Color.DarkGray)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                WhiteCard {
                    Column(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 6.dp, end = 6.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Results: $searchText",
                                lineHeight = 16.sp,
                                color = AppPrime,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 0.dp, bottom = 20.dp, end = 0.dp)
                                    .weight(.7f)
                            )
                            SortMenu(firestoreViewModel)
                            Spacer(modifier = Modifier.width(8.dp))
                            FilterMenu(firestoreViewModel)
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(bottom = 80.dp)
                        ) {
                            items(
                                items = dataState.value?.getOrNull() ?: emptyList(),
                                itemContent = { pattern ->
                                    if (pattern!!.name.contains(searchText, ignoreCase = true)) {
                                        PatternCard2(pattern, navController)
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SortMenu(firestoreViewModel: FirestoreViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val isSortMenuExpanded by firestoreViewModel.isSortMenuExpanded.observeAsState(initial = false)
    expanded = isSortMenuExpanded
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart))
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { firestoreViewModel.setSortMenuExpandedState(!expanded) }){
            Text(
                text = "Sort ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = AppPrime
            )
            Icon(
                painter = painterResource(id = R.drawable.filter_alt),
                contentDescription = "sort",
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                firestoreViewModel.setSortMenuExpandedState(false) },
            modifier = Modifier
                .padding(top =16.dp)
                .background(Color.White)
                .width(screenWidth)
                .border(1.dp, Color.Gray)

        ) {
            Column(modifier = Modifier.padding(all = 8.dp)){
                Text(text = "Sort by:")
            }
        }
    }
}

@Composable
fun FilterMenu(firestoreViewModel: FirestoreViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val isFilterMenuExpanded by firestoreViewModel.isFilterMenuExpanded.observeAsState(initial = false)
    expanded = isFilterMenuExpanded
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart))
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { firestoreViewModel.setFilterMenuExpandedState(!expanded) }) {
            Text(
                text = "Filter ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = AppPrime
            )
            Icon(
                painter = painterResource(id = R.drawable.sort),
                contentDescription = "filter",
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp)
                    .clickable { firestoreViewModel.setFilterMenuExpandedState(!expanded) })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                firestoreViewModel.setFilterMenuExpandedState(false)
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .background(Color.White)
                .width(screenWidth )
                .border(1.dp, Color.Gray)
        ) {
            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(text = "Filter by:")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSearchPage() {
    Crochet3Theme {
        SearchPage(navController = rememberNavController(), firestoreViewModel = FirestoreViewModel())
    }
}


