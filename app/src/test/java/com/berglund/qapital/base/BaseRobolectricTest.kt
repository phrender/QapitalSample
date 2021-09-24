package com.berglund.qapital.base

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.berglund.qapital.QapitalApp
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = QapitalApp::class)
abstract class BaseRobolectricTest {

	fun getApp(): QapitalApp = ApplicationProvider.getApplicationContext()

	fun getApplicationContext(): Context = ApplicationProvider.getApplicationContext()
}
