package com.example.crochet3.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.crochet3.Data.PatternDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class FirestoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    fun getCollection(collectionPath: String) = liveData(Dispatchers.IO) {
        val result = try {
            val snapshot = db.collection(collectionPath).get().await()
            Result.success(snapshot.documents.map { it.toObject(PatternDatabase::class.java) })
        } catch (e: Exception) {
            Result.failure<List<PatternDatabase>>(e)
        }
        emit(result)
    }
    fun getPatternsByCategory(collectionPath: String, category: String) = liveData(Dispatchers.IO) {
        val result = try {
            val snapshot = db.collection(collectionPath).whereEqualTo("category", category).get().await()
            Result.success(snapshot.documents.map { it.toObject(PatternDatabase::class.java) })
        } catch (e: Exception) {
            Result.failure<List<PatternDatabase>>(e)
        }
        emit(result)
    }

    fun getPatternsByIsNew(collectionPath: String, newPattern: Boolean) = liveData(Dispatchers.IO) {
        val result = try {
            val snapshot = db.collection(collectionPath).whereEqualTo("newPattern", newPattern).get().await()
            Result.success(snapshot.documents.map { it.toObject(PatternDatabase::class.java) })
        } catch (e: Exception) {
            Result.failure<List<PatternDatabase>>(e)
        }
        emit(result)
    }

//favorites functions
fun saveFavoritePattern(patternId: String) {
    val currentUser = auth.currentUser
    if (currentUser != null) {
        val userFavoritesRef = db.collection("users").document(currentUser.uid).collection("favorites")
        userFavoritesRef.document(patternId).set(mapOf("isFavorite" to true))
            .addOnSuccessListener {
                Log.d(TAG, "Pattern marked as favorite")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error marking pattern as favorite", e)
            }
    }
}

    // Function to get favorite patterns for the current user
    fun getFavoritePatterns() = liveData(Dispatchers.IO) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userFavoritesRef = db.collection("users").document(currentUser.uid).collection("favorites")
            val result = try {
                val snapshot = userFavoritesRef.get().await()
                val favoritePatterns = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(PatternDatabase::class.java)
                }
                Result.success(favoritePatterns)
            } catch (e: Exception) {
                Result.failure<List<PatternDatabase>>(e)
            }
            emit(result)
        }
    }





    //search dropdown menu state
    // Add expanded state for FilterMenu and SortMenu
    private val _isFilterMenuExpanded = MutableStateFlow(false)
    val isFilterMenuExpanded = _isFilterMenuExpanded.asLiveData(Dispatchers.IO)
    private val _isSortMenuExpanded = MutableStateFlow(false)
    val isSortMenuExpanded = _isSortMenuExpanded.asLiveData(Dispatchers.IO)
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
}
