package com.berglund.qapital.adapters

import androidx.recyclerview.widget.DiffUtil
import com.berglund.qapital.models.FeedModel

class ActivityDiffCallback(
    private val oldList: List<FeedModel>,
    private val newList: List<FeedModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = false

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = false
}