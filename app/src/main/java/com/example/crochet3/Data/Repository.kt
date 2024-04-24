package com.example.crochet3.Data

import kotlinx.coroutines.flow.Flow

class CrochetPatternRepository(private val crochetPatternDao: CrochetPatternDao) {
    fun getAllPatterns(): Flow<List<CrochetPatternEntity>> {
        return crochetPatternDao.getAllPatterns()
    }
    fun updatePattern(pattern: CrochetPatternEntity) {
        crochetPatternDao.updatePattern(pattern)
    }
}