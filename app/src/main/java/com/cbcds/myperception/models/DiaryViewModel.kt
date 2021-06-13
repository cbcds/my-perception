package com.cbcds.myperception.models

import androidx.lifecycle.*
import com.cbcds.myperception.Repository
import com.cbcds.myperception.database.Emotion.Companion.TYPE_NEGATIVE
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.utils.DateUtils
import com.cbcds.myperception.views.DiaryListItem
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: Repository) : ViewModel() {
    val allRecords = repository.getAllEmotionRecords().asLiveData()
    val allEmotions = repository.getAllEmotions().asLiveData()

    private val _listItems = MutableLiveData<List<DiaryListItem>>()
    val listItems: LiveData<List<DiaryListItem>> = _listItems

    fun preprocessListItems() {
        val list = mutableListOf<DiaryListItem>()
        allRecords.value?.groupBy(EmotionRecord::date)
            ?.mapValues { (_, value) -> value.asReversed() }
            ?.forEach { (date, records) ->
                list += DiaryListItem.DateItem(date)
                list.addAll(records.map { DiaryListItem.EmotionRecordItem(it) })
            }
        _listItems.value = list
    }

    fun insert(record: EmotionRecord) {
        viewModelScope.launch {
            repository.insertEmotionRecord(record)
        }
    }

    fun update(record: EmotionRecord) {
        viewModelScope.launch {
            repository.updateEmotionRecord(record)
        }
    }

    fun delete(recordIds: List<Int>) {
        viewModelScope.launch {
            repository.deleteEmotionRecordsById(recordIds)
        }
    }

    fun getChartData(): Array<Any>? {
        return if (allRecords.value != null && allRecords.value!!.isNotEmpty()) {
            val records = allRecords.value!!.reversed()
            val chartData = Array<Any>(records.size + 1) { }
            chartData[0] = arrayOf("", 0)
            var sum = 0
            for (i in records.indices) {
                val name = records[i].name
                val date = DateUtils.dateToString(records[i].date)
                sum += records[i].level
                chartData[i + 1] = arrayOf("$date $name", sum)
            }
            return chartData
        } else null
    }

    fun calculateEmotionLevel(name: String, progress: Int): Int {
        val type = allEmotions.value!!.first { it.name == name }.type
        return if (type == TYPE_NEGATIVE) -1 * (progress + 1) else progress + 1
    }
}

class DiaryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown error occurred")
    }
}