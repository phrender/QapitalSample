package com.berglund.qapital.contracts

interface MainContract {

    interface View {
        fun welcomeMessage(welcomeMessage: String)
    }

    interface Presenter {
        fun onViewCreated()
    }
}