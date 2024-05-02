package com.example.crochet3.Data

import androidx.room.Entity
import com.example.crochet3.R

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
data class CrochetPattern(
    val name: String,
    val newPattern: Boolean,
    val difficulty: Difficulty,
    val category: Category,
    val creatorname: String,
    val creatorlink: String,
    val imageResId: Int,
    val image2: Int,
    val image3: Int,
    val notes: String,
    val timeToComplete: String,
    val materials: String,
    val steps: Int,
    val instructions: String,
)

enum class Difficulty {
    BASIC,
    EASY,
    INTERMEDIATE,
    COMPLEX,
}

enum class Category {
    HATS,
}

val crochetPatterns = listOf(
    CrochetPattern(
        name = "Stripe Hat",
        newPattern = true,
        difficulty = Difficulty.INTERMEDIATE,
        category = Category.HATS,
        creatorname = "Anonymous",
        creatorlink = "https://google.com",
        imageResId = R.drawable.c,
        image2 = R.drawable.c,
        image3 = R.drawable.c,
        notes= "None",
        timeToComplete = "1 Hour",
        materials = "None",
        steps = 3,
        instructions = "add instructions here add instructions here add instructions here add instructions here"),
)