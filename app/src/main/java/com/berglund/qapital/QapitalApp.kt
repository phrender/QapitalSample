package com.berglund.qapital

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class QapitalApp : Application() {

	override fun onCreate() {
		super.onCreate()

		initAndroidThreeTen()

		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
	}

	private fun initAndroidThreeTen() {
		AndroidThreeTen.init(this)
	}
}
