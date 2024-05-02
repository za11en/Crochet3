package com.example.crochet3.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.AppInfo
import com.example.crochet3.CategoriesScreen
import com.example.crochet3.Favorites
import com.example.crochet3.MainScreen
import com.example.crochet3.PatternPage
import com.example.crochet3.Projects
import com.example.crochet3.SearchPage
import com.example.crochet3.SubscreensCategories
import com.example.crochet3.tools.RowCounter
import com.example.crochet3.tools.UnitConversion

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MAINACTIVITY.route) {
        composable(Screens.MAINACTIVITY.route) { MainScreen(navController) }
        composable(Screens.CATEGORYSCREEN.route) { CategoriesScreen(navController) }
        composable(Screens.FAVORITES.route) { Favorites(navController) }
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
        composable(Screens.PROJECTS.route) { Projects(navController) }
        composable("appInfo") { AppInfo(navController) }
        composable("Unit Conversion") { UnitConversion(navController) }
        composable("Row Counter") { RowCounter(navController) }
        composable(Screens.SEARCHPAGE.route) { SearchPage(navController, viewModel()) }
        composable("patternPage/{patternName}") { backStackEntry ->
            val patternName = backStackEntry.arguments?.getString("patternName")
            PatternPage(navController, onNavigateUp = { navController.navigateUp() }, patternName ?: "") }
        composable("subscreensCategories/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            SubscreensCategories(navController, title ?: "") }
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
