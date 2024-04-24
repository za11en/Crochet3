package com.example.crochet3.viewModels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.crochet3.Data.AppDatabase
import com.example.crochet3.Data.Category
import com.example.crochet3.Data.CrochetPatternEntity
import com.example.crochet3.Data.CrochetPatternRepository
import com.example.crochet3.Data.Difficulty
import com.example.crochet3.Data.HookSize
import com.example.crochet3.R
import kotlinx.coroutines.Dispatchers

class CrochetPatternViewModel (application: Application): AndroidViewModel(application) {
    private val crochetPatternRepository: CrochetPatternRepository
    val allPatterns: LiveData<List<CrochetPatternEntity>>
    init {
        val patternDao = AppDatabase.getDatabase(application).crochetPatternDao()
        crochetPatternRepository = CrochetPatternRepository(patternDao)
        allPatterns = crochetPatternRepository.getAllPatterns().asLiveData()
        if (allPatterns.value.isNullOrEmpty()) {
            initializePatterns()
        }
    }
    fun updatePattern(pattern: CrochetPatternEntity) {
        crochetPatternRepository.updatePattern(pattern)
    }

    //subscreen
    fun getPatternsByCategory(category: String) = liveData<List<CrochetPatternEntity>>(Dispatchers.IO) {
        val crochetPatterns = allPatterns.value ?: emptyList()
        val patterns = if (category.equals("All", ignoreCase = true)) {
            crochetPatterns
        } else {
            crochetPatterns.filter { it.category.name.equals(category, ignoreCase = true) }
        }
        emit(patterns)
    }
    @SuppressLint("SuspiciousIndentation")
    private fun initializePatterns() {
        val patterns = listOf(
            CrochetPatternEntity(
                id = 1,
                name = "Amigurumi",
                newPattern = true,
                difficulty = Difficulty.EASY,
                hookSize = HookSize.EIGHTPOINTFIVE,
                category = Category.BLANKETS,
                creatorname = "Anonymous",
                creatorlink = "https://google.com",
                imageResId = R.drawable.amigurumi,
                image2 = R.drawable.a,
                image3 = R.drawable.a,
                notes= "None",
                timeToComplete = "1 Hour",
                materials = "None",
                steps = 4,
                instructions = "add instructions here add instructions here add instructions here add instructions here"
            ),
            CrochetPatternEntity(
                id = 2,
                name = "Flower Hat 2",
                newPattern = true,
                difficulty = Difficulty.COMPLEX,
                hookSize = HookSize.FIVE,
                category = Category.HATS,
                creatorname = "Anonymous",
                creatorlink = "https://google.com",
                imageResId = R.drawable.b,
                image2 = R.drawable.b,
                image3 = R.drawable.b,
                notes= "None",
                timeToComplete = "1 Hour",
                materials = "None",
                steps = 4,
                instructions = "add instructions here add instructions here add instructions here add instructions here" ),
            CrochetPatternEntity(
                id = 3,
                name = "Stripe Hat",
                newPattern = true,
                difficulty = Difficulty.INTERMEDIATE,
                hookSize = HookSize.EIGHTPOINTFIVE,
                category = Category.BLANKETS,
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
            CrochetPatternEntity(
                id = 4,
                name = "Bunny Ears",
                newPattern = false,
                difficulty = Difficulty.EASY,
                hookSize = HookSize.FIVE,
                category = Category.SCARVES,
                creatorname = "Anonymous",
                creatorlink = "https://google.com",
                imageResId = R.drawable.d,
                image2 = R.drawable.d,
                image3 = R.drawable.d,
                notes= "None",
                timeToComplete = "1 Hour",
                materials = "None",
                steps = 3,
                instructions = "add instructions here add instructions here add instructions here add instructions here" ),
            CrochetPatternEntity(
                id = 5,
                name = "Pattern 14",
                newPattern = false,
                difficulty = Difficulty.COMPLEX,
                hookSize = HookSize.FIVE,
                category = Category.COASTERS,
                creatorname = "Anonymous",
                creatorlink = "https://google.com",
                imageResId = R.drawable.f,
                image2 = R.drawable.f,
                image3 = R.drawable.f,
                notes= "None",
                timeToComplete = "1 Hour",
                materials = "None",
                steps = 3,
                instructions = "add instructions here add instructions here add instructions here add instructions here"
            ))
            patterns.forEach { pattern ->
                crochetPatternRepository.updatePattern(pattern)
            }
    }
}