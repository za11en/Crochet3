package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crochet3.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppInfo(navController: NavController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "App Info", drawerState, scope) },
            bottomBar = { BottomBar(navController) },
            containerColor = Color.Transparent,
            modifier = Modifier.background(appGradient()))
        {
            AppInfoContent()
        }
    }
}

@Composable
fun AppInfoContent() {
    Column(
        modifier = Modifier.padding(top = 100.dp)) {
        WhiteCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Crochet Patterns",
                    color = Color.DarkGray,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 32.dp))
                Text(
                    text = "Version 1.0",
                    color = Color.DarkGray,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = "Developed by: Zack Allinson",
                    color = Color.DarkGray,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 48.dp))
                Text(
                    text = "Owned and Operated by: Portkey Media",
                    color = Color.DarkGray,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = "Contact:",
                    color = Color.DarkGray,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 32.dp))
                Text(
                    text = "info@portkey.ca",
                    color = Color.Blue,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
