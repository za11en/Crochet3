package com.example.crochet3.Data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CrochetPatternDao {
    @Query("SELECT * FROM crochet_patterns")
    fun getAllPatterns(): Flow<List<CrochetPatternEntity>>

    @Update
    fun updatePattern(pattern: CrochetPatternEntity)
    @Query("UPDATE crochet_patterns SET isFavorite = :isFavorite WHERE id = :patternId")
    fun updateFavoriteStatus(patternId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM crochet_patterns WHERE isFavorite = 1")
    fun getFavoritePatterns(): Flow<List<CrochetPatternEntity>>

    @Query("SELECT * FROM crochet_patterns WHERE newPattern = 1")
    fun getNewPatterns(): Flow<List<CrochetPatternEntity>>

    @Query("SELECT * FROM crochet_patterns WHERE category = :category")
    fun getPatternsByCategory(category: String): Flow<List<CrochetPatternEntity>>


}
@Database(entities = [CrochetPatternEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun crochetPatternDao(): CrochetPatternDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crochet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
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
class CrochetPatternRepository(private val crochetPatternDao: CrochetPatternDao) {
    fun getAllPatterns(): Flow<List<CrochetPatternEntity>> {
        return crochetPatternDao.getAllPatterns()
    }
    fun updatePattern(pattern: CrochetPatternEntity) {
        crochetPatternDao.updatePattern(pattern)
    }
    fun getFavoritePatterns() = crochetPatternDao.getFavoritePatterns()

    fun getNewPatterns() = crochetPatternDao.getNewPatterns()

    fun getPatternsByCategory(category: String) = crochetPatternDao.getPatternsByCategory(category)
}