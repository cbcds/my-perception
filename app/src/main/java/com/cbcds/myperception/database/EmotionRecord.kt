package com.cbcds.myperception.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class EmotionRecord (
    var name: String
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}