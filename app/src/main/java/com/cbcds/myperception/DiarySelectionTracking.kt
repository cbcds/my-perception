package com.cbcds.myperception

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.cbcds.myperception.adapters.DiaryAdapter

class DiaryItemLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long> {
        val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return EMPTY_ITEM
        return (recyclerView.getChildViewHolder(view) as DiaryAdapter.DiaryItemViewHolder)
            .getItemDetails()
    }

    object EMPTY_ITEM : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getSelectionKey(): Long = -1
        override fun getPosition(): Int = Integer.MAX_VALUE
    }
}

class DiaryItemKeyProvider(private val recyclerView: RecyclerView) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long? {
        return recyclerView.adapter?.getItemId(position)
    }

    override fun getPosition(key: Long): Int {
        val viewHolder = recyclerView.findViewHolderForItemId(key)
        return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
    }
}

fun buildDiarySelectionTracker(recyclerView: RecyclerView): SelectionTracker<Long> {
    return SelectionTracker.Builder(
        "diary-item-selection",
        recyclerView,
        DiaryItemKeyProvider(recyclerView),
        DiaryItemLookup(recyclerView),
        StorageStrategy.createLongStorage()
    ).withSelectionPredicate(
        object : SelectionTracker.SelectionPredicate<Long>() {
            override fun canSelectMultiple(): Boolean = true

            override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean =
                key != DiaryItemLookup.EMPTY_ITEM.selectionKey

            override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean =
                position != DiaryItemLookup.EMPTY_ITEM.position
        }
    ).build()
}