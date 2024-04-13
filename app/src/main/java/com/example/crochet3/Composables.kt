package com.example.crochet3


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        .padding(top = 32.dp, start = 16.dp), style = Typography.titleLarge)
                }
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp) ) },
                    label = { Text(text = "Home", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) },
                    label = { Text(text = "Favorites", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) } ,
                    label = { Text(text = "Search", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Build, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) } ,
                    label = { Text(text = "Tools", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Menu, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) } ,
                    label = { Text(text = "Patterns", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.PlayArrow, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) } ,
                    label = { Text(text = "Tutorials", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, top = 8.dp, end=16.dp, bottom = 8.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Info, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(36.dp)) } ,
                    label = { Text(text = "Info", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {}
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





