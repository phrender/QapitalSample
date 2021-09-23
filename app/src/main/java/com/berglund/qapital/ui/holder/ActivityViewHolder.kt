package com.berglund.qapital.ui.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.berglund.qapital.R
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.ActivityModel
import com.berglund.qapital.models.FeedModel

class ActivityViewHolder(
    private val binding: ActivityListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedModel) {
        binding.activityEntryMessage.text = item.message
        binding.activityEntryAmount.text = String.format("$ %.2f", item.amount)
        binding.activityEntryDate.text = item.timestamp
        binding.activityEntryUserImage
            .load(item.avatarUrl) {
                networkCachePolicy(CachePolicy.ENABLED)
                placeholder(R.drawable.ic_account_img)
                crossfade(true)
            }
    }
}