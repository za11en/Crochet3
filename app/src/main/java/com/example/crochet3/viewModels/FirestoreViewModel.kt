package com.example.crochet3.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await


data class PatternDatabase(
    val category: String = "",
    val creatorlink: String = "",
    val creatorname: String = "",
    val difficulty: String = "",
    val hookSize: String = "",
    val image2: String = "",
    val image3: String = "",
    val imageResId: String = "",
    val instructions: String = "",
    var isFavorite: Boolean = false,
    val materials: String = "",
    val name: String = "",
    val newPattern: Boolean = false,
    val notes: String = "",
    val steps: String = "",
    val timeToComplete: String = ""
)
class FirestoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    fun getCollection(collectionPath: String) = liveData(Dispatchers.IO) {
        val result = try {
            val snapshot = db.collection(collectionPath).get().await()
            Result.success(snapshot.documents.map { it.toObject(PatternDatabase::class.java) })
        } catch (e: Exception) {
            Result.failure<List<PatternDatabase>>(e)
        }
        emit(result)
    }

}
