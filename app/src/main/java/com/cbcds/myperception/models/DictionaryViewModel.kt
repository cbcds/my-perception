package com.cbcds.myperception.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.cbcds.myperception.Repository

class DictionaryViewModel(private val repository: Repository) : ViewModel() {
    val allEmotions = repository.allEmotions.asLiveData()
}

class DictionaryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown error occurred")
    }
}