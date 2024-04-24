package com.example.crochet3.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crochet_patterns")
data class CrochetPatternEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    var isFavorite: Boolean = false,
    val newPattern: Boolean,
    val difficulty: Difficulty,
    val hookSize: HookSize,
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
