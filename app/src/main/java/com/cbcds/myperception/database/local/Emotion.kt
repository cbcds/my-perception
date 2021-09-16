package com.cbcds.myperception.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotions")
data class Emotion(
    @PrimaryKey var name: String,
    var description: String,
    var type: String
) {
    companion object {
        const val TYPE_POSITIVE = "P"
        const val TYPE_NEGATIVE = "N"
    }
}