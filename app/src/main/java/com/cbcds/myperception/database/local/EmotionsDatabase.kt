package com.cbcds.myperception.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cbcds.myperception.utils.Converters

// TODO: delete exportSchema after finishing database implementation
@Database(entities = [EmotionRecord::class, Emotion::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EmotionsDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun emotionsDao(): EmotionsDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionsDatabase? = null

        fun getDatabase(context: Context): EmotionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionsDatabase::class.java,
                    "emotions-database"
                ).createFromAsset("emotions.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}