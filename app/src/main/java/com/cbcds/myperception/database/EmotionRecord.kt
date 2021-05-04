package com.cbcds.myperception.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "diary")
data class EmotionRecord(
    var name: String,
    var date: Date,
    var details: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}