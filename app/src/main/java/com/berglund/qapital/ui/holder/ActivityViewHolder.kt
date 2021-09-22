package com.berglund.qapital.ui.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.berglund.qapital.R
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.ActivityModel

class ActivityViewHolder(
    private val binding: ActivityListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ActivityModel) {
        binding.activityEntryMessage.text = item.message
        binding.activityEntryAmount.text = String.format("$ %.2f", item.amount)
        binding.activityEntryDate.text = item.timestamp
        binding.activityEntryUserImage // TODO: Create a feed object which has merged UserData and ActivityData
            .load("http://qapital-ios-testtask.herokuapp.com/avatars/mikael.jpg") {
                placeholder(R.drawable.ic_account_img)
                crossfade(true)
            }
    }
}