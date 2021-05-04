package com.cbcds.myperception.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionsDao {
    @Query("SELECT * FROM emotions")
    fun getAll(): Flow<List<Emotion>>
}