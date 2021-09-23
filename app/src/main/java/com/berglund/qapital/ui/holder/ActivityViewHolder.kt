package com.berglund.qapital.ui.holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.berglund.qapital.R
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.extensions.setHtmlText
import com.berglund.qapital.models.FeedModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ActivityViewHolder(
    private val binding: ActivityListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedModel) {
        binding.activityEntryMessage.setHtmlText(item.message)
        binding.activityEntryAmount.text = String.format("$ %.2f", item.amount)
        binding.activityEntryDate.text = formatEntryDate(item.timestamp)
        binding.activityEntryUserImage
            .load(item.avatarUrl) {
                networkCachePolicy(CachePolicy.ENABLED)
                placeholder(R.drawable.ic_account_img)
                crossfade(true)
            }
    }

    private fun formatEntryDate(timestamp: LocalDateTime): String {
        val currentDate = LocalDateTime.now().withHour(0).withMinute(0)
        if (timestamp.isAfter(currentDate)) {
            return "Today"
        } else if (timestamp.isBefore(currentDate.minusDays(1)) && timestamp.isAfter(currentDate.minusDays(2))) {
            return "Yesterday"
        }

        return timestamp.format(DateTimeFormatter.ofPattern("D MMM YYYY"))
    }
}