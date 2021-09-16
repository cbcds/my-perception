package com.cbcds.myperception

import android.app.Application
import com.cbcds.myperception.database.local.EmotionsDatabase

class EmotionsApplication : Application() {
    private val database by lazy { EmotionsDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.diaryDao(), database.emotionsDao()) }
}