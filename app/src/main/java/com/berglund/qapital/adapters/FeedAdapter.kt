package com.berglund.qapital.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berglund.qapital.databinding.ActivityListItemBinding
import com.berglund.qapital.models.FeedEntryModel
import com.berglund.qapital.ui.holder.ActivityViewHolder

class FeedAdapter : RecyclerView.Adapter<ActivityViewHolder>() {

	companion object {
		private const val NEXT_PAGE_OFFSET = 3
	}

	var nextPageListener: NextPageLoaderListener? = null
	private var feedList: MutableList<FeedEntryModel> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
		val binding = ActivityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ActivityViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
		checkFetchNextPage(position)
		holder.bind(feedList[position])
	}

	override fun getItemCount(): Int = feedList.size

	private fun checkFetchNextPage(position: Int) {
		nextPageListener?.let {
			if (position >= feedList.size - NEXT_PAGE_OFFSET && feedList.isNotEmpty()) {
				it.loadNextPage()
			}
		}
	}

	fun updateFeedList(newList: List<FeedEntryModel>) {
		val diffCallback = ActivityDiffCallback(feedList, newList)
		val diffResult = DiffUtil.calculateDiff(diffCallback)

		feedList.clear()
		feedList.addAll(newList)
		diffResult.dispatchUpdatesTo(this)
	}
}
