package com.cbcds.myperception.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// TODO: delete exportSchema after finishing database implementation
@Database(entities = [EmotionRecord::class], version = 1, exportSchema = false)
abstract class EmotionsDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionsDatabase? = null

        fun getDatabase(context: Context): EmotionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionsDatabase::class.java,
                    "emotions-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}