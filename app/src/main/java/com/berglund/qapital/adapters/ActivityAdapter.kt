package com.berglund.qapital.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.ActivityModel
import com.berglund.qapital.ui.holder.ActivityViewHolder

class ActivityAdapter(itemList: List<ActivityModel>) : RecyclerView.Adapter<ActivityViewHolder>() {

    private var activityList: MutableList<ActivityModel> = itemList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ActivityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = activityList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = activityList.size

    fun updateActivityList(newList: List<ActivityModel>) {
        val diffCallback = ActivityDiffCallback(activityList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        activityList.clear()
        activityList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}