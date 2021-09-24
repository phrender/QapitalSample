package com.berglund.qapital.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.ActivityModel
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.models.FeedModel
import com.berglund.qapital.ui.holder.ActivityViewHolder

class FeedAdapter(itemList: List<FeedEntryModel>) : RecyclerView.Adapter<ActivityViewHolder>() {

    private var feedList: MutableList<FeedEntryModel> = itemList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ActivityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = feedList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = feedList.size

    fun updateFeedList(newList: List<FeedEntryModel>) {
        val diffCallback = ActivityDiffCallback(feedList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        //feedList.clear()
        feedList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}