package com.cbcds.myperception.views

import com.cbcds.myperception.database.EmotionRecord
import java.util.*

sealed class DiaryListItem(val type: Int) {
    companion object {
        const val TYPE_DATE = 1
        const val TYPE_EMOTION_RECORD = 2
    }

    class DateItem(val date: Date) : DiaryListItem(TYPE_DATE)
    class EmotionRecordItem(val record: EmotionRecord) : DiaryListItem(TYPE_EMOTION_RECORD)
}