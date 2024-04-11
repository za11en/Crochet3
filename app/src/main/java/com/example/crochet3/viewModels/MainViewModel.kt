package com.example.crochet3.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crochet3.CrochetPattern
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel:ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _patterns = MutableStateFlow(listOf<CrochetPattern>())
    val patterns = searchText.combine(_patterns) { text, patterns ->
        if(text.isBlank()){
            patterns
        } else {
            patterns.filter{
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _patterns.value

        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}
