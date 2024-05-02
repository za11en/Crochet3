package com.example.crochet3.tools

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.BottomBar
import com.example.crochet3.Drawer
import com.example.crochet3.TopAppBar
import com.example.crochet3.WhiteCard
import com.example.crochet3.appGradient

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RowCounter (navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "Row Counter", drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient())
        ) {
            Column(modifier = Modifier.padding(top = 100.dp)) {
                WhiteCard {}
            }
        }
    }
}

@Preview
@Composable
fun RowCounterPreview () {
    val navController = rememberNavController()
    RowCounter(navController)
}