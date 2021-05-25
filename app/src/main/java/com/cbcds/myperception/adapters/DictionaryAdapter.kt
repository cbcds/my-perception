package com.cbcds.myperception.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cbcds.myperception.R
import com.cbcds.myperception.database.Emotion
import com.cbcds.myperception.databinding.ItemDictionaryBinding

class DictionaryAdapter(private val context: Context) :
    ListAdapter<Emotion, DictionaryAdapter.DictionaryItemViewHolder>(DictionaryItemComparator()) {
    companion object {
        private var selectedPositions = mutableSetOf<Int>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryItemViewHolder {
        return DictionaryItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DictionaryItemViewHolder, position: Int) {
        holder.bind(getItem(position), position, context)
        holder.binding.cardEmotion.setOnClickListener {
            if (position !in selectedPositions) {
                selectedPositions.add(position)
            } else {
                selectedPositions.remove(position)
            }
            notifyItemChanged(position)
        }
    }

    class DictionaryItemViewHolder(var binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Emotion, position: Int, context: Context) {
            binding.tvEmotionName.text = item.name

            if (position in selectedPositions) {
                binding.tvDescription.text = item.description
                binding.layoutDescription.visibility = View.VISIBLE
                animate(context)
            } else if (binding.layoutDescription.isVisible) {
                binding.layoutDescription.visibility = View.GONE
                animate(context)
            }
        }

        private fun animate(context: Context) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.anim_expand_list)
            binding.layoutDescription.startAnimation(animation)
        }

        companion object {
            fun create(parent: ViewGroup): DictionaryItemViewHolder {
                val binding = ItemDictionaryBinding.inflate(LayoutInflater.from(parent.context))
                return DictionaryItemViewHolder(binding)
            }
        }
    }
}

class DictionaryItemComparator : DiffUtil.ItemCallback<Emotion>() {
    override fun areItemsTheSame(oldItem: Emotion, newItem: Emotion): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Emotion, newItem: Emotion): Boolean = true
}