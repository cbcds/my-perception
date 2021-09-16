package com.cbcds.myperception.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cbcds.myperception.Repository
import com.cbcds.myperception.database.local.EmotionRecord
import com.cbcds.myperception.utils.DateUtils
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyStatsViewModel(private val repository: Repository) : ViewModel() {
    enum class TimePeriod(val daysNum: Long) { WEEK(7L), MONTH(30L) }

    var timePeriod = TimePeriod.WEEK
        set(newTimePeriod) {
            if (newTimePeriod == field) return

            field = newTimePeriod
            viewModelScope.launch {
                updateData()
            }
        }

    val data = MutableLiveData<List<EmotionRecord>>()

    init {
        viewModelScope.launch {
            updateData()
        }
    }

    fun getChartDataAsync(): Deferred<Array<Any>?> {
        return viewModelScope.async(Dispatchers.IO) {
            if (data.value != null && data.value!!.isNotEmpty()) {
                val records = data.value!!
                val chartData = Array<Any>(records.size + 1) { }
                chartData[0] = arrayOf("", 0)
                var sum = 0
                for (i in records.indices) {
                    val name = records[i].name
                    val date = DateUtils.dateToString(records[i].date)
                    sum += records[i].level
                    chartData[i + 1] = arrayOf("$date $name", sum)
                }
                chartData
            } else null
        }
    }

    private suspend fun updateData() {
        val (begin, end) = DateUtils.getTimePeriodBoundaries(timePeriod.daysNum)
        repository.getEmotionRecordsByDate(begin, end).collect {
            data.value = it
        }
    }
}

class MyStatsViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyStatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown error occurred")
    }
}