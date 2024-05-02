package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crochet3.viewModels.FirestoreViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubscreensCategories(navController: NavController, title: String) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel: FirestoreViewModel = viewModel()
    val patterns = viewModel.getPatternsByCategory("PatternDatabase", title).observeAsState(initial = null)
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, title, drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient())
        ) {
            Column(modifier = Modifier.padding(top = 100.dp)) {
                WhiteCard {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 16.dp, end = 10.dp, bottom = 80.dp)
                    ) {
                        patterns.value?.let {result ->
                            if (result.isSuccess){
                                result.getOrNull()?.let { list ->
                                    items(list) { pattern ->
                                        if (pattern != null) {
                                            PatternCard2(pattern, navController)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSubscreensCategories() {
    val navController = rememberNavController()
    Crochet3Theme {
        SubscreensCategories(navController, "Category Test")
    }
}


