package com.cbcds.myperception.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date DESC")
    fun getAll(): Flow<List<EmotionRecord>>

    @Query("SELECT * FROM diary WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getByDate(startDate: Date, endDate: Date): Flow<List<EmotionRecord>>

    @Insert
    suspend fun insert(record: EmotionRecord)

    @Update
    suspend fun update(record: EmotionRecord)

    @Query("DELETE FROM diary WHERE ID IN (:recordIds)")
    suspend fun delete(recordIds: List<Int>)
}