package com.berglund.qapital.di

import com.berglund.qapital.contracts.MainContract
import com.berglund.qapital.ui.main.MainActivity
import com.berglund.qapital.ui.main.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
@InstallIn(ActivityComponent::class)
@ExperimentalCoroutinesApi
abstract class MainModule {

    @Binds
    abstract fun bindActivity(activity: MainActivity): MainContract.View

    @FlowPreview
    @Binds
    abstract fun bindPresenter(presenter: MainPresenter): MainContract.Presenter
}