package com.example.crochet3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
                    .padding(start = 16.dp, top = 24.dp, bottom = 24.dp, end = 24.dp)
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
            //Search bar
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
                //SEARCHBAR END
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
                                .padding(top = 4.dp)
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 20.dp,
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
                            .padding(start = 10.dp, top = 8.dp, end = 10.dp, bottom = 80.dp)
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



//a preview of the main screen
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Crochet3Theme {
        MainScreen(navController = rememberNavController())
    }
}
