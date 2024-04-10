package com.example.crochet3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.Typography

@Composable
fun TopAppBar(navController: NavController, title: String) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp, start = 14.dp, bottom = 16.dp, end = 60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .background(Color(0x20990040), RoundedCornerShape(12.dp)))
        {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp))
        }
        Text(
            text = title,
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}



@Composable
fun BottomBar(navController: NavController) {
    val currentRoute = navController.currentDestination?.route
    BottomAppBar(
        containerColor = Color.White,
        modifier = Modifier.shadow(elevation = 8.dp),
        actions = {
            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomBarItem(navController, currentRoute, "main", Icons.Filled.Home, "Home")
                BottomBarItem(navController, currentRoute, "favorites", Icons.Filled.Favorite, "Favorites")
                BottomBarItem(navController, currentRoute, "searchPage", Icons.Filled.Search, "Search")
                BottomBarItem(navController, currentRoute, "tools", Icons.Filled.Build, "Tools")
                BottomBarItem(navController, currentRoute, "categoriesScreen", Icons.Filled.Menu, "Patterns")
            }
        }
    )
}

@Composable
fun BottomBarItem(navController: NavController, currentRoute: String?, route: String, icon: ImageVector, label: String) {
    val isSelected = currentRoute?.startsWith(route) ?: false
    val color = if (isSelected) Color.White else Color.Gray
    val textColor = if (isSelected) AppPrime else Color.Gray
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {
            if (route == "searchPage") {
                navController.navigate("$route/Search") // Append "/Search" when navigating
            } else {
                navController.navigate(route)
            }
        },
            modifier = if (isSelected) Modifier.background(Brush.linearGradient(colors = listOf(
                AppPrimeSecond,
                AppPrimeThird
            )), RoundedCornerShape(50)) else Modifier) {
            if (route == "categoriesScreen") {
                Icon(
                    painter = painterResource(id = R.drawable.grid_view),
                    contentDescription = "Localized description",
                    tint = color,
                    modifier = Modifier.size(34.dp)
                )
            } else {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Text(label,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold)
    }
}
@Composable
fun appGradient(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            AppPrimeSecond,
            AppPrimeThird,
            AppPrimeThird,
            AppPrimeThird,
            AppPrimeThird,
        )
    )
}
@Composable
fun WhiteCard(content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .background(Color.Transparent, RoundedCornerShape(36.dp, 36.dp, 0.dp, 0.dp))
            .shadow(elevation = 40.dp)
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        content()
    }
}


@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}

@Preview
@Composable
fun TopMenuPreview() {
    TopAppBar(navController = rememberNavController(), title = "Home")
}

@Preview
@Composable
fun WhiteCardPreview() {
    WhiteCard(content={})
}