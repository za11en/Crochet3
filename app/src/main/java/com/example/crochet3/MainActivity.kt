package com.example.crochet3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.Typography
import com.example.crochet3.ui.theme.Poppins
import com.example.crochet3.ui.theme.TitleTiny
import com.example.crochet3.viewModels.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Crochet3Theme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val patterns by viewModel.patterns.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                val isFavorite by viewModel.isFavorite.collectAsState()
                AppNavHost()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        MainContent(navController)
    }
}

@Composable
fun MainContent(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        AppPrimeSecond,
                        AppPrimeThird,
                        AppPrimeThird,
                    )
                )
            )
    ) {
        val searchText = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, top = 32.dp, bottom = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Crochet Patterns",
                    fontFamily = Poppins,
                    lineHeight = 28.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.help_outline),
                    contentDescription = "help icon",
                    tint = AppPrimeThird,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 2.dp)
                        .clickable { navController.navigate("appinfo") }
                )
            }
            Surface(
                color = Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                val isFocused = remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {
                        if (searchText.value.isNotEmpty()) {
                            navController.navigate("searchPage/${searchText.value}")
                        }
                    }, modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    BasicTextField(
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        cursorBrush = SolidColor(Color.Black),
                        textStyle = TextStyle(color = Color.Black, fontSize = 24.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 50.dp, top = 10.dp, bottom = 10.dp, end = 50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .onFocusChanged { focusState ->
                                isFocused.value = focusState.isFocused
                            },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (searchText.value.isNotEmpty()) {
                                navController.navigate("searchPage/${searchText.value}")
                            }
                        })
                    )
                    if (searchText.value.isEmpty() && !isFocused.value) {
                        Text(
                            text = "Search Patterns",
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(start = 50.dp)
                                .align(Alignment.CenterStart)
                        )
                    }
                    if (!isFocused.value) {
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
                    if (searchText.value.isEmpty() && isFocused.value) {
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
                    if (searchText.value.isNotEmpty() && isFocused.value) {
                        Icon(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = "clear search",
                            tint = Color.Gray,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(42.dp)
                                .padding(end = 10.dp)
                                .clickable { searchText.value = "" }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Popular Categories",
                    lineHeight = 20.sp,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
                        .fillMaxWidth(.7f)
                )
                Text(
                    text = "See All",
                    style = Typography.titleSmall,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 32.dp, start = 24.dp, bottom = 8.dp, end = 16.dp)
                        .fillMaxWidth(.8f)
                        .clickable { navController.navigate("categoriesScreen") }
                )
            }

            val imageIds = listOf(
                R.drawable.g,
                R.drawable.f,
                R.drawable.amigurumi,
                R.drawable.socks,
                R.drawable.blanket   /* add more image IDs here */
            )
            LazyRow(modifier = Modifier.padding(start = 16.dp)) {
                itemsIndexed(
                    listOf(
                        "Hats",
                        "Scarves",
                        "Amigurumi",
                        "Socks",
                        "Blankets"
                    )
                ) { index, item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 14.dp, bottom = 16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .height(90.dp)
                                .width(90.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { navController.navigate("subscreensCategories/${item}") }
                                .shadow(20.dp, shape = RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                painter = painterResource(id = imageIds[index % imageIds.size]),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Crochet Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                        Text(
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            text = item,
                            style = Typography.titleSmall,
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 0.dp)
                                .fillMaxWidth(1f)
                        )
                    }
                }
            }
            WhiteCard {
                Column(modifier = Modifier.background(Color.White)) {
                    Text(
                        text = "Featured Patterns",
                        fontFamily = Poppins,
                        lineHeight = 20.sp,
                        color = AppPrime,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            top = 24.dp,
                            start = 20.dp,
                            bottom = 2.dp,
                            end = 10.dp
                        ),
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppPrimeSecond,
                                    AppPrimeThird
                                )
                            )
                        )
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 6.dp, end = 10.dp, bottom = 80.dp)
                    ) {
                        items(crochetPatterns) { pattern ->
                            PatternCard(pattern, navController)
                        }
                    }
                }
            }
        }
    }
}


//the card used for displaying the patterns used throughout the app
@Composable
fun PatternCard(pattern:  CrochetPattern, navController: NavController) {
    val isFavorite = remember { mutableStateOf(false) }
    Box {
        Card(modifier = Modifier
            .padding(6.dp)
            .border(6.dp, Color.White, RoundedCornerShape(16.dp))
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .height(175.dp)
            .width(175.dp)
            .clickable { navController.navigate("patternPage/${pattern.name}") }) {
            Image(
                painter = painterResource(id = pattern.imageResId),
                contentDescription = "Crochet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .weight(1f)
            )
            Row {
                Column(
                    modifier = Modifier
                        .padding(start = 6.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text(
                        text = pattern.name,
                        style = Typography.titleSmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                    Text(
                        text = pattern.creatorname,
                        style = TitleTiny,
                        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                    )
                }

            }
        }
        Row( modifier = Modifier
            .fillMaxWidth(.85f)
            .padding(top = 18.dp, end = 0.dp),verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End) {
            FloatingActionButton(
                onClick = { isFavorite.value = !isFavorite.value},
                modifier = Modifier
                    .size(28.dp),
                containerColor = Color.White
            ){ Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "heart icon",
                tint = if (isFavorite.value) Color.Red else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
            )
            }
        }
    }
}

//a preview of the main screen
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Crochet3Theme {
        MainScreen(navController = rememberNavController())
    }
}
