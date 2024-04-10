package com.example.crochet3.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.crochet3.crochetPatterns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Subscreen viewmodel
class PatternViewModel : ViewModel() {
    fun getPatternsByCategory(category: String) = liveData(Dispatchers.IO) {
        val patterns = if (category.equals("All", ignoreCase = true)) {
            crochetPatterns
        } else {
            crochetPatterns.filter { it.category.name.equals(category, ignoreCase = true) }
        }
        emit(patterns)
    }
}

//tools page viewmodel
class ToolsViewModel : ViewModel() {
    val toolNames = listOf("Row Counter", "Unit Conversion", "UK-US", "Hook and Yarn Sizing", "Crochet Definitions")
    fun navigateToTool(navController: NavController, toolName: String) {
        viewModelScope.launch {
            navController.navigate(toolName)
        }
    }
}

//category page viewmodel
class CategoryViewModel : ViewModel() {
    fun getCategories() = liveData(Dispatchers.IO) {
        val categories = listOf(
            "Hats",
            "Scarves",
            "Gloves",
            "Shawls",
            "Blankets",
            "Bags",
            "Amigurumi",
            "Coasters",
            "Socks",
            "Decor",
            "Miscellaneous",
            "All")
        emit(categories)
    }
}

