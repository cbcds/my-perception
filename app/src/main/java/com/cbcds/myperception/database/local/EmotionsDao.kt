package com.cbcds.myperception.database.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionsDao {
    @Query("SELECT * FROM emotions ORDER BY name ASC")
    fun getAll(): Flow<List<Emotion>>
}