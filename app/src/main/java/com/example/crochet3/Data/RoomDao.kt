package com.example.crochet3.Data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CrochetPatternDao {
    @Query("SELECT * FROM crochet_patterns")
    fun getAllPatterns(): Flow<List<CrochetPatternEntity>>

    @Update
    fun updatePattern(pattern: CrochetPatternEntity)
}

