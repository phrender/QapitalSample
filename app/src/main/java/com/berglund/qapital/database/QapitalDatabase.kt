package com.berglund.qapital.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berglund.qapital.database.converters.ActivityConverter
import com.berglund.qapital.database.converters.StorageInfoConverter
import com.berglund.qapital.database.converters.UserConverter
import com.berglund.qapital.database.dao.ActivityDao
import com.berglund.qapital.database.dao.UserDao
import com.berglund.qapital.database.entities.ActivitiesDbEntity
import com.berglund.qapital.database.entities.UserDbEntity

@Database(
	entities = [
		ActivitiesDbEntity::class,
		UserDbEntity::class],
	version = 1,
	exportSchema = true)
@TypeConverters(
	ActivityConverter::class,
	UserConverter::class,
	StorageInfoConverter::class
)
abstract class QapitalDatabase : RoomDatabase() {

	abstract fun getActivitiesDao(): ActivityDao

	abstract fun getUsersDao(): UserDao
}
