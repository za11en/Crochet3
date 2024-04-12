package com.example.crochet3


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun Drawer () {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                AppPrimeSecond,
                                AppPrimeThird,
                            )
                        )
                    )) {
                    Text("Crochet App", color = Color.White,  modifier = Modifier
                        .padding(16.dp), style = Typography.titleLarge)
                }
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Localized description") },
                    label = { Text(text = "Home", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Localized description") },
                    label = { Text(text = "Favorites", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Localized description") },
                    label = { Text(text = "Search", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Build, contentDescription = "Localized description") },
                    label = { Text(text = "Tools", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Menu, contentDescription = "Localized description") },
                    label = { Text(text = "Patterns", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.PlayArrow, contentDescription = "Localized description") },
                    label = { Text(text = "Tutorials", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Info, contentDescription = "Localized description") },
                    label = { Text(text = "Help", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Delete, contentDescription = "Localized description") },
                    label = { Text(text = "Exit", style = Typography.titleSmall) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )

            }
        }
    ) {}
}

@Preview
@Composable
fun DrawerPreview() {
    Drawer()
}






