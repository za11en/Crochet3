package com.example.crochet3.Data

import com.example.crochet3.R


enum class Screens(val route: String) {
    MAINACTIVITY("main"),
    CATEGORYSCREEN("categoriesScreen"),
    SEARCHPAGE("searchPage"),
    SUBSCREENSCATEGORIES("subscreensCategories"),
    FAVORITES("favorites"),
    PATTERNPAGE("patternPage"),
    APPINFO("appInfo"),
    HATS("hats"),
    SCARVES("scarves"),
    GLOVES("gloves"),
    SHAWLS("shawls"),
    BLANKETS("blankets"),
    BAGS("bags"),
    AMIGURUMI("amigurumi"),
    COASTERS("coasters"),
    SOCKS("socks"),
    DECOR("decor"),
    MISCELLANEOUS("miscellaneous"),
    ALL("all"),
    MYPROJECTS("myProjects"),
}
data class CrochetPattern(
    val name: String,
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
) {
    fun doesMatchSearchQuery(query:String): Boolean {
        val matchingCombinations = listOf(
            name,
            "$category",
            creatorname,
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

enum class Difficulty {
    BASIC,
    EASY,
    INTERMEDIATE,
    COMPLEX,
}

enum class Category {
    HATS,
    SCARVES,
    GLOVES,
    SHAWLS,
    BLANKETS,
    BAGS,
    AMIGURUMI,
    COASTERS,
    SOCKS,
    DECOR,
    MISCELLANEOUS,
}

enum class HookSize(val size: String) {
    TWOPOINTFIVE("2.5mm"),
    FIVE("5.0mm"),
    EIGHTPOINTFIVE("8.5mm"),
}

val crochetPatterns = listOf(
    CrochetPattern(
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
        instructions = "add instructions here add instructions here add instructions here add instructions here"),
    CrochetPattern(
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
    CrochetPattern(
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
    CrochetPattern(
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
    CrochetPattern(
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
        instructions = "add instructions here add instructions here add instructions here add instructions here" ),
)