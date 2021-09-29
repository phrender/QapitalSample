package com.berglund.qapital.di

import com.berglund.qapital.database.dao.ActivityDao
import com.berglund.qapital.database.dao.UserDao
import com.berglund.qapital.network.QapitalApi
import com.berglund.qapital.repository.ActivitiesRepository
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.storage.ActivitiesStorage
import com.berglund.qapital.storage.RoomStorage
import com.berglund.qapital.storage.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
@ExperimentalCoroutinesApi
object RepositoryModule {

	@Provides
	@ViewModelScoped
	fun provideUserRepository(api: QapitalApi, userDao: UserDao): UserRepository = UserRepository(api, RoomStorage(UserStorage(userDao)))

	@Provides
	@ViewModelScoped
	fun provideActivitiesRepository(api: QapitalApi, activitiesDao: ActivityDao) = ActivitiesRepository(api, RoomStorage(ActivitiesStorage(activitiesDao)))
}
