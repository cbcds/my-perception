package com.cbcds.myperception.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cbcds.myperception.databinding.ItemDateBinding
import com.cbcds.myperception.databinding.ItemEmotionRecordBinding
import com.cbcds.myperception.utils.DateUtils.Companion.format
import com.cbcds.myperception.views.DiaryListItem

class DiaryAdapter :
    ListAdapter<DiaryListItem, DiaryAdapter.DiaryItemViewHolder>(DiaryItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryItemViewHolder {
        return DiaryItemViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: DiaryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    class DiaryItemViewHolder(private var binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DiaryListItem) {
            when (item) {
                is DiaryListItem.DateItem ->
                    with(binding as ItemDateBinding) {
                        tvDate.text = item.date.format()
                    }
                is DiaryListItem.EmotionRecordItem ->
                    with(binding as ItemEmotionRecordBinding) {
                        tvEmotionName.text = item.record.name
                        tvEmotionDetails.text = item.record.details
                    }
            }
        }

        companion object {
            fun create(parent: ViewGroup, viewType: Int): DiaryItemViewHolder {
                return when (viewType) {
                    DiaryListItem.TYPE_DATE -> {
                        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context))
                        DiaryItemViewHolder(binding)
                    }
                    DiaryListItem.TYPE_EMOTION_RECORD -> {
                        val binding =
                            ItemEmotionRecordBinding.inflate(LayoutInflater.from(parent.context))
                        DiaryItemViewHolder(binding)
                    }
                    else -> throw IllegalArgumentException("Unknown viewType: $viewType")
                }
            }
        }
    }
}

class DiaryItemComparator : DiffUtil.ItemCallback<DiaryListItem>() {
    override fun areItemsTheSame(oldItem: DiaryListItem, newItem: DiaryListItem): Boolean {
        if (oldItem.type != newItem.type) {
            return false
        }

        return when (oldItem) {
            is DiaryListItem.DateItem ->
                oldItem.date == (newItem as DiaryListItem.DateItem).date
            is DiaryListItem.EmotionRecordItem ->
                oldItem.record.id == (newItem as DiaryListItem.EmotionRecordItem).record.id
        }
    }

    override fun areContentsTheSame(oldItem: DiaryListItem, newItem: DiaryListItem): Boolean {
        return when (oldItem) {
            is DiaryListItem.DateItem -> true
            is DiaryListItem.EmotionRecordItem ->
                oldItem.record == (newItem as DiaryListItem.EmotionRecordItem).record
        }
    }
}