package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.viewModels.FirestoreViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyProjects(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "My Projects", drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient())
        ) {
            Column(modifier = Modifier.padding(top = 100.dp)) {
                WhiteCard {
                    val viewModel : FirestoreViewModel = viewModel()
                    val collectionPath = "PatternDatabase"
                    val dataState = viewModel.getCollection(collectionPath).observeAsState(initial = null)
                    when {
                        dataState.value?.isSuccess == true -> {
                            dataState.value?.getOrNull()?.let { list ->
                                list.forEach { item ->
                                    if (item != null) {
                                        Text(text = item.imageResId, modifier = Modifier.padding(8.dp))
                                        if (item.newPattern) {
                                            Text(text = "true", color = Color.Green)
                                        } else {
                                            Text(text = "false", color = Color.Red)
                                        }
                                        if (item.isFavorite) {
                                            Text(text = "true", color = Color.Green)
                                        } else {
                                            Text(text = "false", color = Color.Red)
                                        }

                                    }
                                }
                            }
                        }
                        dataState.value?.isFailure == true -> {
                            Text(text = "Error: ${dataState.value?.exceptionOrNull()?.message}")
                        }
                        else -> {
                            CircularProgressIndicator(modifier = Modifier
                                .padding(top = 32.dp)
                                .align(CenterHorizontally))
                        }
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun MyProjectsPreview() {
    val navController = rememberNavController()
    MyProjects(navController)
}

