package com.example.crochet3


enum class Screens(val route: String) {
    MAINACTIVITY("main"),
    CATEGORYSCREEN("categoriesScreen"),
    SEARCHPAGE("searchPage"),
    SUBSCREENSCATEGORIES("subscreensCategories"),
    FAVORITES("favorites"),
    PATTERNPAGE("patternPage"),
    TOOLS("tools"),
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
}

data class CrochetPattern(
    val name: String,
    val newPattern: Boolean,
    val featured: Boolean,
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
    val instructions: String
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
    BEGINNER,
    EASY,
    INTERMEDIATE,
    HARD,
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
    THREE("3.0mm"),
    THREEPOINTFIVE("3.5mm"),
    FOUR("4.0mm"),
    FOURPOINTFIVE("4.5mm"),
    FIVE("5.0mm"),
    FIVEPOINTFIVE("5.5mm"),
    SIX("6.0mm"),
    SIXPOINTFIVE("6.5mm"),
    SEVEN("7.0mm"),
    SEVENPOINTFIVE("7.5mm"),
    EIGHT("8.0mm"),
    EIGHTPOINTFIVE("8.5mm"),
    NINE("9.0mm"),
    NINEPOINTFIVE("9.5mm"),
    TEN("10.0mm")
}

