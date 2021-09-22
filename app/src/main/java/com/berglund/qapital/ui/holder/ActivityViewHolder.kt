package com.berglund.qapital.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.ActivityModel

class ActivityViewHolder(
    private val binding: ActivityListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ActivityModel) {
        binding.activityEntryMessage.text = item.message
        binding.activityEntryAmount.text = "$ ${item.amount}"
        binding.activityEntryDate.text = item.timestamp
    }
}