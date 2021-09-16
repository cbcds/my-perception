package com.cbcds.myperception

import com.cbcds.myperception.database.local.DiaryDao
import com.cbcds.myperception.database.local.Emotion
import com.cbcds.myperception.database.local.EmotionRecord
import com.cbcds.myperception.database.local.EmotionsDao
import kotlinx.coroutines.flow.Flow
import java.util.*

class Repository(private val diaryDao: DiaryDao, private val emotionsDao: EmotionsDao) {
    fun getAllEmotions(): Flow<List<Emotion>> = emotionsDao.getAll()

    fun getAllEmotionRecords(): Flow<List<EmotionRecord>> = diaryDao.getAll()

    fun getEmotionRecordsByDate(startDate: Date, endDate: Date): Flow<List<EmotionRecord>> =
        diaryDao.getByDate(startDate, endDate)

    suspend fun insertEmotionRecord(record: EmotionRecord) = diaryDao.insert(record)

    suspend fun updateEmotionRecord(record: EmotionRecord) = diaryDao.update(record)

    suspend fun deleteEmotionRecordsById(recordIds: List<Int>) = diaryDao.delete(recordIds)
}