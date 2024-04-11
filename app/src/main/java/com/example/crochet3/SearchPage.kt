package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.navigation.NavController
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.crochet3.ui.theme.AppPrime
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crochet3.viewModels.SearchViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(navController: NavController,initialSearchText: String, searchViewModel: SearchViewModel = viewModel()) {
    val isFocused by searchViewModel.isFocused.observeAsState(initial = false)
    val searchText by searchViewModel.searchText.observeAsState(initial = "")
    val patterns by searchViewModel.searchResults.observeAsState(initial = emptyList())
    Scaffold(
        topBar = { TopAppBar(navController, "Search") },
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Transparent,
        modifier = Modifier.background(appGradient())
    ) {
        Column(modifier = Modifier.padding(top = 100.dp)) {
            Surface(
                color = Color.White, shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                SearchBar(searchText, isFocused, searchViewModel, navController)
            }
            Spacer(modifier = Modifier.height(32.dp))
            WhiteCard {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp),
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
                            text = "Search Results For: $searchText",
                            lineHeight = 16.sp,
                            color = AppPrime,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(top = 20.dp, start = 0.dp, bottom = 20.dp, end = 0.dp)
                                .weight(.7f)
                        )
                        FilterMenu(searchViewModel)
                        Spacer(modifier = Modifier.width(8.dp))
                        SortMenu(searchViewModel)
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), modifier = Modifier.padding(bottom = 80.dp)
                    ) {
                        items(patterns) { pattern ->
                            PatternCard(pattern, navController)
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

}
@Composable
fun SearchBar(
        searchText: String,
        isFocused: Boolean,
        searchViewModel: SearchViewModel,
        navController: NavController)
{
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            if (searchText.isNotEmpty()) {
                navController.navigate("searchPage/$searchText")
            } }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
            BasicTextField(
                value = searchText,
                onValueChange = { searchViewModel.setSearchText(it) },
                cursorBrush = SolidColor(Color.Black),
                textStyle = TextStyle(color = Color.Black, fontSize = 24.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 50.dp, top = 10.dp, bottom = 10.dp, end = 50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .onFocusChanged { focusState ->
                        searchViewModel.setFocusedState(focusState.isFocused)
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (searchText.isNotEmpty()) {
                        navController.navigate("searchPage/${searchText}")
                    }
                })
            )
            if (searchText.isEmpty() && !isFocused) {
                Text(
                    text = "Search Patterns",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .align(Alignment.CenterStart))
            }
            if (!isFocused) {
                Icon(
                    painter = painterResource(id = R.drawable.voice),
                    contentDescription = "voice search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(42.dp)
                        .padding(end = 10.dp)
                        .clickable { /*TODO*/ })
            }
            if (searchText.isEmpty() && isFocused) {
                Icon(
                    painter = painterResource(id = R.drawable.voice),
                    contentDescription = "voice search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(42.dp)
                        .padding(end = 10.dp)
                        .clickable { /*TODO*/ }
                )
            }
            if (searchText.isNotEmpty() && isFocused) {
                Icon(
                    painter = painterResource(id = R.drawable.clear),
                    contentDescription = "clear search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(42.dp)
                        .padding(end = 10.dp)
                        .clickable { searchViewModel.setSearchText("") }
                )
            }
        }
    }

@Composable
fun SortMenu(searchViewModel: SearchViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val isSortMenuExpanded by searchViewModel.isSortMenuExpanded.observeAsState(initial = false)
    expanded = isSortMenuExpanded
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val items = searchViewModel.sortItems
    val checkedItems = searchViewModel.checkedSortItems
    items.forEach { label -> checkedItems[label] = false }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart))
    {
        Icon(
            painter = painterResource(id = R.drawable.sort),
            contentDescription = "sort",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(36.dp)
                .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp)
                .clickable { searchViewModel.setSortMenuExpandedState(!expanded) })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                searchViewModel.setSortMenuExpandedState(false) },
            modifier = Modifier
                .background(Color.White)
                .width(screenWidth / 2)
        ) {
            Text(text = "Filter by:") }
    }
}


@Composable
fun FilterMenu(searchViewModel: SearchViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val isFilterMenuExpanded by searchViewModel.isFilterMenuExpanded.observeAsState(initial = false)
    expanded = isFilterMenuExpanded
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val items = searchViewModel.filterItems
    val checkedItems = searchViewModel.checkedFilterItems
    items.forEach { label ->
        checkedItems[label] = false
    }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart))
    {
        Icon(
            painter = painterResource(id = R.drawable.filter_alt),
            contentDescription = "filter",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(36.dp)
                .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp)
                .clickable { searchViewModel.setFilterMenuExpandedState(!expanded) })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                searchViewModel.setFilterMenuExpandedState(false) },
            modifier = Modifier
                .background(Color.White)
                .width(screenWidth / 2)
        ) {
            Text(text = "Filter by:") }
        }
    }



@Preview(showBackground = true)
@Composable
fun PreviewSearchPage() {
    Crochet3Theme {
        SearchPage(navController = rememberNavController(), initialSearchText = "")
    }
}


