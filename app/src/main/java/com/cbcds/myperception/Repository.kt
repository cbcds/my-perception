package com.cbcds.myperception

import com.cbcds.myperception.database.DiaryDao
import com.cbcds.myperception.database.Emotion
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.database.EmotionsDao
import kotlinx.coroutines.flow.Flow

class Repository(private val diaryDao: DiaryDao, private val emotionsDao: EmotionsDao) {
    val allEmotions: Flow<List<Emotion>> = emotionsDao.getAll()

    val allEmotionRecords: Flow<List<EmotionRecord>> = diaryDao.getAll()

    suspend fun insertEmotionRecord(record: EmotionRecord) {
        diaryDao.insert(record)
    }

    suspend fun updateEmotionRecord(record: EmotionRecord) {
        diaryDao.update(record)
    }

    suspend fun deleteEmotionRecordsById(recordIds: List<Int>) {
        diaryDao.delete(recordIds)
    }
}