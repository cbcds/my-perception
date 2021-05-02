package com.cbcds.myperception.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getAll(): Flow<List<EmotionRecord>>

    @Insert
    suspend fun insert(record: EmotionRecord)
}