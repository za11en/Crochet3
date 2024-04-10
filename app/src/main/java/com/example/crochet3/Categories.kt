package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.AppPrime
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crochet3.viewModels.CategoryViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(navController: NavController) {
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.getCategories().observeAsState(initial = emptyList())
    Scaffold(
        topBar = {TopAppBar(navController, "Categories")},
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Transparent,
        modifier = Modifier.background(appGradient()))
    {
        Column(modifier = Modifier.padding(top = 100.dp))
        {
            WhiteCard {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom =80.dp))
                {
                    itemsIndexed(categories.value) { index, title ->
                        CategoryCard(
                            title = title,
                            navController = navController,
                            destination = "subscreensCategories"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(title: String, navController: NavController, destination: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .padding(6.dp)
                .height(160.dp)
                .width(175.dp)
                .background(Color.Transparent)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .clickable(onClick = { navController.navigate("$destination/$title") })
        ) {
            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = "Crochet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
                    .weight(1f)) }
        Text(
            textAlign = TextAlign.Center,
            text = title,
            color = AppPrime,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoriesScreen() {
    val navController = rememberNavController()
    Crochet3Theme {
        CategoriesScreen(navController)
    }
}