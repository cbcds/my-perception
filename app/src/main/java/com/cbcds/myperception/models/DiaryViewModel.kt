package com.cbcds.myperception.models

import androidx.lifecycle.*
import com.cbcds.myperception.Repository
import com.cbcds.myperception.database.local.Emotion.Companion.TYPE_NEGATIVE
import com.cbcds.myperception.database.local.EmotionRecord
import com.cbcds.myperception.views.DiaryListItem
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: Repository) : UserViewModel() {
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

    fun calculateEmotionLevel(name: String, progress: Int): Int {
        val type = allEmotions.value!!.first { it.name == name }.type
        return if (type == TYPE_NEGATIVE) -1 * (progress + 1) else progress + 1
    }
}

class DiaryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown error occurred")
    }
}