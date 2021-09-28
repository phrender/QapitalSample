package com.berglund.qapital.di

import android.content.Context
import androidx.room.Room
import com.berglund.qapital.database.QapitalDatabase
import com.berglund.qapital.database.dao.ActivityDao
import com.berglund.qapital.database.dao.UserDao
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.storage.ActivitiesStorage
import com.berglund.qapital.storage.LocalStorage
import com.berglund.qapital.storage.RoomStorage
import com.berglund.qapital.storage.Storage
import com.berglund.qapital.storage.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

	@Provides
	@Singleton
	fun provideActivitesRoomStorage(activitiesStorage: ActivitiesStorage): RoomStorage<ActivitiesModel, Storage<ActivitiesModel>> = RoomStorage(activitiesStorage)

	@Provides
	@Singleton
	fun provideUserRoomStorage(userStorage: UserStorage): RoomStorage<UserModel, Storage<UserModel>> = RoomStorage(userStorage)

	@Provides
	fun provideActivitiesStorage(activityDao: ActivityDao): ActivitiesStorage = ActivitiesStorage(activityDao)

	@Provides
	fun provideUserStorage(userDao: UserDao): UserStorage = UserStorage(userDao)

	@Provides
	fun provideActivitiesDao(database: QapitalDatabase): ActivityDao = database.getActivitiesDao()

	@Provides
	fun provideUsersDao(database: QapitalDatabase): UserDao = database.getUsersDao()

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext appContext: Context) =
		Room.databaseBuilder(appContext, QapitalDatabase::class.java, "QapitalDb").build()
}
