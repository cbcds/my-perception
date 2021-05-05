package com.cbcds.myperception.models

import androidx.lifecycle.*
import com.cbcds.myperception.Repository
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.views.DiaryListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiaryViewModel(private val repository: Repository) : ViewModel() {
    val allRecords = repository.allEmotionRecords.asLiveData()
    //var oldRecordsListSize = allRecords.value?.size

    private val _listItems = MutableLiveData<List<DiaryListItem>>()
    val listItems: LiveData<List<DiaryListItem>> = _listItems

    suspend fun preprocessListItems() {
        with(viewModelScope) {
            val listDeferred = async {
                withContext(Dispatchers.IO) {
                    val list = mutableListOf<DiaryListItem>()
                    allRecords.value?.groupBy(EmotionRecord::date)?.forEach { (date, records) ->
                        list += DiaryListItem.DateItem(date)
                        list.addAll(records.map { DiaryListItem.EmotionRecordItem(it) })
                    }
                    return@withContext list
                }
            }
            launch { _listItems.value = listDeferred.await() }
        }
    }

    fun insert(record: EmotionRecord) {
        viewModelScope.launch {
            repository.insertEmotionRecord(record)
        }
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