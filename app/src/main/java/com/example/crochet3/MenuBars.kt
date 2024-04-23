package com.example.crochet3

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.Data.Category
import com.example.crochet3.Data.CrochetPattern
import com.example.crochet3.Data.Difficulty
import com.example.crochet3.Data.HookSize
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.TitleTiny
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
                .background(Color(0x20000000), RoundedCornerShape(12.dp)))
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
fun TopAppBarWithShare(navController: NavController, pattern: CrochetPattern, shareLauncher: ActivityResultLauncher<Intent>) {
    val isFavorite = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(top = 24.dp, start = 14.dp, bottom = 16.dp, end = 14.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .background(Color(0x20000000), RoundedCornerShape(12.dp))
        )
        {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.navigateUp() })
        }
        Text(
            text = "",
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShareButton(onClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this pattern I found on the Crochet App: ${pattern.name}"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                shareLauncher.launch(shareIntent)
            }, size = 44)
            Spacer(modifier = Modifier.width(24.dp))
            FavoriteButton(isFavorite = isFavorite, size = 44)
            Spacer(modifier = Modifier.width(8.dp))
        }
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
fun getScreenWidth(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
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
        shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .background(Color.Transparent, RoundedCornerShape(0.dp, 36.dp, 0.dp, 0.dp))
            .shadow(elevation = 40.dp)
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        content()
    }
}

@Composable
fun FavoriteButton(isFavorite: MutableState<Boolean>, size: Int) {
    FloatingActionButton(
        onClick = {isFavorite.value = !isFavorite.value},
        shape = CircleShape,
        modifier = Modifier
            .shadow(8.dp, shape = CircleShape)
            .size(size.dp),
        containerColor = Color.White){
        Icon(
            tint = if (isFavorite.value) Color.Red else Color.Gray,
            imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isFavorite.value) "Remove from favorites" else "Add to favorites",
            modifier = Modifier
                .padding(top = 2.dp)
                .size((size *.70).dp)
        )
    }
}
@Composable
fun ShareButton(onClick: () -> Unit, size : Int) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(size.dp),
        containerColor = Color.White
    ) {
        Icon(
            Icons.Filled.Share,
            tint = Color.Black,
            contentDescription = "Share",
            modifier = Modifier
                .padding(end = 8.dp)
                .size((size *.70).dp)
        )
    }
}

@Composable
fun NewRibbon() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 24.dp)
            .background(Color.Red)
            .size(52.dp, 20.dp)
    ) {
        Text(
            text = "New",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, end = 12.dp)
        )
    }
}
@Composable
fun PatternCard(pattern: CrochetPattern, navController: NavController) {
    val isFavorite = remember { mutableStateOf(false) }
    Box {
        Card(modifier = Modifier
           .padding(6.dp)
            .border(6.dp, Color.White, RoundedCornerShape(16.dp))
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .height(175.dp)
            .width(getScreenWidth() /2 )
            .clickable { navController.navigate("patternPage/${pattern.name}") }){
            Image(
                painter = painterResource(id = pattern.imageResId),
                contentDescription = "Crochet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .weight(1f)
            )
            Row {
                Column(
                    modifier = Modifier
                        .padding(start = 6.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text(
                        text = pattern.name,
                        style = Typography.titleSmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                    Text(
                        text = pattern.creatorname,
                        style = TitleTiny,
                        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                    )
                }
            }
        }
        Box()
        {
                NewRibbon()
        }
        Row( modifier = Modifier
            .width(getScreenWidth() /2)
            .padding(top = 18.dp, end = 18.dp),verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End) {
            FavoriteButton(isFavorite = isFavorite, size = 30)
        }
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
fun PatternCardPreview() {
    val previewtest = CrochetPattern(
        "test",
        true,
        true,
        Difficulty.EASY,
        HookSize.TWOPOINTFIVE,
        Category.HATS,
        "test",
        "1",
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        "test",
        "test",
        "test",
        "")
    val navController = rememberNavController()
    PatternCard(navController = navController, pattern =  previewtest)
}
@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    val isFavorite = remember { mutableStateOf(false) }
    FavoriteButton(isFavorite = isFavorite, size = 100)
}
@Preview(showBackground = true)
@Composable
fun ShareButtonPreview() {
    ShareButton(onClick = {}, size = 100)
}

@Preview (showBackground = true)
@Composable
fun NewRibbonPreview() {
    NewRibbon()
}
@Preview
@Composable
fun WhiteCardPreview() {
    WhiteCard(content={})
}