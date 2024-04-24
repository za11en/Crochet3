package com.example.crochet3.viewModels

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.crochet3.Data.crochetPatterns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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
//SearchPageViewModel
class SearchViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asLiveData(Dispatchers.IO)
    val searchResults = _searchText
        .map { searchText ->
            crochetPatterns.filter { it.name.contains(searchText, ignoreCase = true) }
        }
        .asLiveData(Dispatchers.IO)
    // SortMenu data
    val sortItems = listOf("By Name", "By Difficulty")
    val checkedSortItems = mutableStateMapOf<String, Boolean>().apply {
        sortItems.forEach { label ->
            this[label] = false
        }
    }
    // Add isFocused state
    private val _isFocused = MutableStateFlow(false)
    val isFocused = _isFocused.asLiveData(Dispatchers.IO)
    // Add expanded state for FilterMenu and SortMenu
    private val _isFilterMenuExpanded = MutableStateFlow(false)
    val isFilterMenuExpanded = _isFilterMenuExpanded.asLiveData(Dispatchers.IO)
    private val _isSortMenuExpanded = MutableStateFlow(false)
    val isSortMenuExpanded = _isSortMenuExpanded.asLiveData(Dispatchers.IO)
    fun setFocusedState(isFocused: Boolean) {
        viewModelScope.launch {
            _isFocused.emit(isFocused)
        }
    }
    fun setFilterMenuExpandedState(isExpanded: Boolean) {
        viewModelScope.launch {
            _isFilterMenuExpanded.emit(isExpanded)
        }
    }
    fun setSortMenuExpandedState(isExpanded: Boolean) {
        viewModelScope.launch {
            _isSortMenuExpanded.emit(isExpanded)
        }
    }
    // FilterMenu data
    val filterItems = listOf("Easy", "Intermediate", "Advanced", "Expert")
    val checkedFilterItems = mutableStateMapOf<String, Boolean>().apply {
        filterItems.forEach { label ->
            this[label] = false
        }
    }
    fun setSearchText(newText: String) {
        viewModelScope.launch {
            _searchText.emit(newText)
        }
    }
}

