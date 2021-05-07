package com.cbcds.myperception.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date DESC")
    fun getAll(): Flow<List<EmotionRecord>>

    @Insert
    suspend fun insert(record: EmotionRecord)

    @Query("DELETE FROM diary WHERE ID IN (:recordIds)")
    suspend fun delete(recordIds: List<Int>)
}