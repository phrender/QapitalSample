package com.berglund.qapital.adapters

import androidx.recyclerview.widget.DiffUtil
import com.berglund.qapital.models.ActivityModel

class ActivityDiffCallback(
    private val oldList: List<ActivityModel>,
    private val newList: List<ActivityModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].userId == newList[newItemPosition].userId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].userId == newList[newItemPosition].userId
}