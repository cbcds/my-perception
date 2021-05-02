package com.cbcds.myperception.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cbcds.myperception.Repository
import com.cbcds.myperception.database.EmotionRecord
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: Repository) : ViewModel() {
    val allRecords = repository.allEmotionRecords.asLiveData()

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