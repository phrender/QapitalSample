package com.berglund.qapital.contracts

import com.berglund.qapital.adapters.NextPageLoaderListener
import com.berglund.qapital.models.FeedEntryModel

interface MainContract {

	interface View {
		fun updateFeedList(feed: List<FeedEntryModel>)

		fun isLoadingData(isLoading: Boolean)
	}

	interface Presenter {

		fun loadFeed()

		fun getScrollListener(): NextPageLoaderListener
	}
}
