package com.berglund.qapital.contracts

import com.berglund.qapital.models.FeedModel

interface MainContract {

    interface View {
        fun updateFeedList(feed: List<FeedModel>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}