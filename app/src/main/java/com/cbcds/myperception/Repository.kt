package com.cbcds.myperception

import com.cbcds.myperception.database.DiaryDao
import com.cbcds.myperception.database.EmotionRecord
import kotlinx.coroutines.flow.Flow

class Repository(private val diaryDao: DiaryDao) {
    val allEmotionRecords: Flow<List<EmotionRecord>> = diaryDao.getAll()

    suspend fun insertEmotionRecord(record: EmotionRecord) {
        diaryDao.insert(record)
    }
}