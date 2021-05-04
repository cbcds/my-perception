package com.cbcds.myperception.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotions")
data class Emotion(
    @PrimaryKey var name: String,
    var description: String
)