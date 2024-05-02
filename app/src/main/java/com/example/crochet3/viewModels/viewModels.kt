package com.example.crochet3.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

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
            "Socks",
            "Decor",
            "Miscellaneous")
        emit(categories)
    }
}


