package com.example.crochet3.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
