package com.cbcds.myperception.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.ItemEmotionRecordBinding

class DiaryAdapter :
    ListAdapter<EmotionRecord, DiaryAdapter.EmotionRecordViewHolder>(EmotionRecordComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionRecordViewHolder {
        return EmotionRecordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmotionRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EmotionRecordViewHolder(private var binding: ItemEmotionRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: EmotionRecord) {
            binding.tvEmotionName.text = record.name
            binding.tvEmotionDetails.text = record.details
        }

        companion object {
            fun create(parent: ViewGroup): EmotionRecordViewHolder {
                val binding = ItemEmotionRecordBinding.inflate(LayoutInflater.from(parent.context))
                return EmotionRecordViewHolder(binding)
            }
        }
    }

    class EmotionRecordComparator : DiffUtil.ItemCallback<EmotionRecord>() {
        override fun areItemsTheSame(oldItem: EmotionRecord, newItem: EmotionRecord): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EmotionRecord, newItem: EmotionRecord): Boolean {
            return oldItem.name == newItem.name
        }
    }
}