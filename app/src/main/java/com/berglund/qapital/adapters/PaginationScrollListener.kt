package com.berglund.qapital.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
	private val linearLayoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)

		val visibleScrollCount = linearLayoutManager.childCount
		val totalItemCount = linearLayoutManager.itemCount
		val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

		if (!isLoading()) {
			if ((visibleScrollCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
				loadMoreItems()
			}
		}
	}

	protected abstract fun loadMoreItems()
	abstract fun isLoading(): Boolean
}
