package com.berglund.qapital.di

import android.app.Activity
import com.berglund.qapital.ui.main.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

	@Provides
	fun bindActivity(activity: Activity): MainActivity = activity as MainActivity
}
