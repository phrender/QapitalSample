package com.berglund.qapital.contracts

import com.berglund.qapital.models.ActivityModel

interface MainContract {

    interface View {
        fun updateActivityList(activities: List<ActivityModel>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}