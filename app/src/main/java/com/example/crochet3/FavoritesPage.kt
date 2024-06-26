package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.viewModels.FirestoreViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Favorites(navController: NavController, viewModel: FirestoreViewModel = viewModel()){
    val dataState = viewModel.getCollection("PatternDatabase").observeAsState(initial = null)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "Favorites", drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient())
        ) {
            Column(modifier = Modifier.padding(top = 100.dp))
            {
                WhiteCard {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 8.dp, end = 10.dp, bottom = 80.dp)
                    ) {
                        when {
                            dataState.value?.isSuccess == true -> {
                                dataState.value?.getOrNull()?.let { list ->
                                    items(list) { pattern ->
                                        if (pattern != null) {
                                            PatternCard2(pattern, navController)
                                        }
                                    }
                                }
                            }
                            else -> {
                                item {LoadingIndicator()}
                            }// Handle other states...
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun FavoritesPreview(){
    val navController = rememberNavController()
    Favorites(navController)
}