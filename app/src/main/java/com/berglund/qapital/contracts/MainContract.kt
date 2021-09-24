package com.berglund.qapital.contracts

import androidx.recyclerview.widget.LinearLayoutManager
import com.berglund.qapital.adapters.PaginationScrollListener
import com.berglund.qapital.models.FeedEntryModel

interface MainContract {

    interface View {
        fun updateFeedList(feed: List<FeedEntryModel>)
    }

    interface Presenter {
        fun onViewCreated()

        fun getScrollListener(linearLayoutManager: LinearLayoutManager): PaginationScrollListener
    }
}