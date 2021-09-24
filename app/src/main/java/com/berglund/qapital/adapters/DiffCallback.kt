package com.berglund.qapital.adapters

import androidx.recyclerview.widget.DiffUtil
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.models.FeedModel

class ActivityDiffCallback(
    private val oldList: List<FeedEntryModel>,
    private val newList: List<FeedEntryModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].timestamp == newList[newItemPosition].timestamp &&
                oldList[oldItemPosition].amount == newList[newItemPosition].amount
}