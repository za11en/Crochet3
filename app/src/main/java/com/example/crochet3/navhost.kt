package com.example.crochet3

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val sharedPrefManager = SharedPreferencesRepository(context)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MAINACTIVITY.route) {
        composable(Screens.MAINACTIVITY.route) { MainScreen(navController) }
        composable(Screens.CATEGORYSCREEN.route) { CategoriesScreen(navController) }
        composable("subscreensCategories/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") // Retrieve the title argument
            SubscreensCategories(navController, title ?: "") // Pass the title to the SubscreenCategories composable
        }
        composable(Screens.FAVORITES.route) { Favorites(navController, sharedPrefManager) }
        composable("searchPage/{searchText}") { backStackEntry ->
            val searchText = backStackEntry.arguments?.getString("searchText")
            SearchPage(navController, searchText ?: "")
        }
        composable(Screens.HATS.route) { Hats() }
        composable(Screens.SCARVES.route) { Scarves() }
        composable(Screens.GLOVES.route) { Gloves() }
        composable(Screens.SHAWLS.route) { Shawls() }
        composable(Screens.BLANKETS.route) { Blankets() }
        composable(Screens.BAGS.route) { Bags() }
        composable(Screens.AMIGURUMI.route) { Toys() }
        composable(Screens.COASTERS.route) { Coasters() }
        composable(Screens.SOCKS.route) { Socks() }
        composable(Screens.DECOR.route) { Decor() }
        composable(Screens.MISCELLANEOUS.route) { Miscellaneous() }
        composable("patternPage/{patternName}") { backStackEntry ->
            val patternName = backStackEntry.arguments?.getString("patternName")
            PatternPage(navController, patternName ?: "", sharedPrefManager)
        }
        composable(Screens.TOOLS.route) { Tools(navController) }
        composable(Screens.APPINFO.route) { AppInfo(navController) }
        composable(Screens.ALL.route) { All() }
    }
}

@Composable
fun Hats() {}

@Composable
fun Scarves() {}

@Composable
fun Gloves() {}

@Composable
fun Shawls() {}

@Composable
fun Blankets() {}

@Composable
fun Bags() {}

@Composable
fun Toys() {}

@Composable
fun Coasters() {}

@Composable
fun Socks() {}

@Composable
fun Decor() {}

@Composable
fun Miscellaneous() {}

@Composable
fun All() {}