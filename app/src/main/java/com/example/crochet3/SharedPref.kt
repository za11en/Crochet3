package com.example.crochet3

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferencesRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorite_patterns", Context.MODE_PRIVATE)
    private val _favoritePatterns = MutableStateFlow<Set<String>>(emptySet())
    val favoritePatterns: Flow<Set<String>> = _favoritePatterns.asStateFlow()

    init {
        val favorites = sharedPreferences.getStringSet("favorite_patterns", emptySet()) ?: emptySet()
        _favoritePatterns.value = favorites
    }

    fun addFavorite(patternName: String) {
        val favorites = sharedPreferences.getStringSet("favorite_patterns", emptySet()) ?: emptySet()
        val updatedFavorites = favorites + patternName
        sharedPreferences.edit().putStringSet("favorite_patterns", updatedFavorites).apply()
        _favoritePatterns.value = updatedFavorites
    }

    fun removeFavorite(patternName: String) {
        val favorites = sharedPreferences.getStringSet("favorite_patterns", emptySet()) ?: emptySet()
        val updatedFavorites = favorites - patternName
        sharedPreferences.edit().putStringSet("favorite_patterns", updatedFavorites).apply()
        _favoritePatterns.value = updatedFavorites
    }

    fun isFavorite(patternName: String): Boolean {
        val favorites = sharedPreferences.getStringSet("favorite_patterns", emptySet()) ?: emptySet()
        return patternName in favorites
    }



}
