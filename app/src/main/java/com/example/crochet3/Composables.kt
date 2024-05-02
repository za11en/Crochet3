package com.example.crochet3

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.crochet3.Data.Category
import com.example.crochet3.Data.CrochetPattern
import com.example.crochet3.Data.Difficulty
import com.example.crochet3.Data.PatternDatabase
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.TitleTiny
import com.example.crochet3.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(navController: NavController, title: String, drawerState: DrawerState, scope: CoroutineScope) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp, start = 14.dp, bottom = 16.dp, end = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.background(Color(0x20000000), RoundedCornerShape(12.dp)))
        {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp))
        }
        Text(
            text = title,
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Icon(
            Icons.Filled.Menu,
            contentDescription = "Localized description",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
                .clickable { scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } }
        )
    }
}
@Composable
fun TopAppBarWithShare(
    onNavigateUp: () -> Unit,
    pattern: CrochetPattern,
    shareLauncher: ActivityResultLauncher<Intent>) {
    val isFavorite = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(top = 24.dp, start = 14.dp, bottom = 16.dp, end = 14.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onNavigateUp()},
            modifier = Modifier
                .background(Color(0x20000000), RoundedCornerShape(12.dp))
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = "",
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
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
                BottomBarItem(navController, currentRoute, "Projects", Icons.Filled.AccountCircle, "Projects")
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
        IconButton(onClick = { navController.navigate(route)},
            modifier = if (isSelected) Modifier.background(Brush.linearGradient(colors = listOf(
                AppPrimeSecond,
                AppPrimeThird
            )), RoundedCornerShape(50)) else Modifier) {
            if (route == "categoriesScreen") {
                Icon(
                    painter = painterResource(id = R.drawable.inventory),
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
    val ribbonShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width/2f, size.height/3f)
        lineTo(size.width/2f, 0f)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Red)
            .size(44.dp, 22.dp)

    ) {
        Text(
            text = "New",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
    Box(
        modifier = Modifier
            .padding(top = 22.dp)
            .size(20.dp)
            .clip(ribbonShape)
            .background(Color(0xFF990000))

    )

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
        if (pattern.newPattern) {
            Box( modifier = Modifier
                .offset(x = (-4).dp, y = 20.dp)) {
                NewRibbon()
            }
        }
        Row( modifier = Modifier
            .width(getScreenWidth() /2)
            .padding(top = 18.dp, end = 18.dp),verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End) {
            FavoriteButton(isFavorite = isFavorite, size = 30)
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun PatternCard2(pattern: PatternDatabase, navController: NavController) {
    val isFavorite = remember { mutableStateOf(pattern.isFavorite) }
    Box {
        Card(modifier = Modifier
            .padding(6.dp)
            .border(6.dp, Color.White, RoundedCornerShape(16.dp))
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .height(175.dp)
            .width(getScreenWidth() /2 )
            .clickable { navController.navigate("patternPage/${pattern.name}") }){
            Image(
                painter = rememberImagePainter(
                    data = pattern.imageResId,
                    builder = {
                        crossfade(false)
                    }
                ),
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
        if (pattern.newPattern) {
            Box( modifier = Modifier
                .offset(x = (-4).dp, y = 20.dp)) {
                NewRibbon()
            }
        }
        Row( modifier = Modifier
            .width(getScreenWidth() /2)
            .padding(top = 18.dp, end = 18.dp),verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End) {
            FavoriteButton(isFavorite = isFavorite, size = 30)
        }
    }
}
@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(strokeWidth = 4.dp, color = AppPrime,modifier = Modifier
        .padding(12.dp)
        .size(32.dp)
        .height(32.dp)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun DrawerPreview(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    Drawer(navController, drawerState, scope) {
        Scaffold(
            topBar = { TopAppBar(navController, "Home", drawerState, scope) },
            bottomBar = { BottomBar(navController) }
        ) {
            Column {
                Text(" ")
            }
        }
    }

}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Drawer(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (drawerContainerColor = Color.White) {
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
                    Text("Crochet3", color = Color.White,  modifier = Modifier
                        .padding(top = 40.dp, start = 32.dp), style = Typography.titleLarge)
                }
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(28.dp) ) },
                    label = { Text(text = "Home", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    shape = RoundedCornerShape(16.dp, 0.dp, 0.dp,16.dp) ,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("main")}
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(28.dp)) },
                    label = { Text(text = "Favorites", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("favorites")}
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(28.dp)) } ,
                    label = { Text(text = "Search", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("searchPage")}
                )
                NavigationDrawerItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.inventory), contentDescription = "Localized description", modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(28.dp)) } ,
                    label = { Text(text = "Patterns", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("categoriesScreen")}
                )
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, top = 8.dp, end=16.dp))
                Text("Tools", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, color = Color.Black,  modifier = Modifier
                    .padding(top = 8.dp, start = 32.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calculate), contentDescription = "Localized description", modifier = Modifier
                                .fillMaxHeight(1f)
                                .width(28.dp)) } ,
                    label = { Text(text = "Row Counter", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("Row Counter")}
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calculate), contentDescription = "Localized description", modifier = Modifier
                                .fillMaxHeight(1f)
                                .width(28.dp)) } ,
                    label = { Text(text = "Unit Conversion", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("Unit Conversion")}
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calculate), contentDescription = "Localized description", modifier = Modifier
                                .fillMaxHeight(1f)
                                .width(28.dp)) } ,
                    label = { Text(text = "Hook and Yarn Sizing", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("tools")}
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calculate), contentDescription = "Localized description", modifier = Modifier
                                .fillMaxHeight(1f)
                                .width(28.dp)) } ,
                    label = { Text(text = "Sizing", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp).height(40.dp),
                    onClick = {navController.navigate("tools")}
                )
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, top = 8.dp, end=16.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            Icons.Outlined.Info, contentDescription = "Localized description", modifier = Modifier
                                .fillMaxHeight(1f)
                                .width(28.dp)) } ,
                    label = { Text(text = "Info", fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, modifier = Modifier.padding(start = 16.dp)) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedContainerColor = AppPrimeThird,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp),
                    onClick = { navController.navigate("appinfo")}
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = { BottomBar(navController) }
        ) {
            content()
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
    val scope = rememberCoroutineScope()
    TopAppBar(navController = rememberNavController(), title = "Home", drawerState = DrawerState(DrawerValue.Closed), scope = scope)
}
@Preview
@Composable
fun PatternCardPreview() {
    val previewtest = CrochetPattern(
        "test",
        true,
        Difficulty.EASY,
        Category.HATS,
        "test",
        "1",
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        "test",
        "test",
        "test",
        3,
        "test")
    val navController = rememberNavController()
    PatternCard(navController = navController, pattern =  previewtest)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: SearchBarColors = SearchBarDefaults.colors(),
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .background(colors.containerColor, shape = RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.invoke()
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                placeholder()
            }
            trailingIcon?.invoke()
        }
    }
}
@Composable
fun Placeholder(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth()
    )
}
