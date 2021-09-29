package com.berglund.qapital.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.berglund.qapital.database.converters.StorageInfoConverter
import com.berglund.qapital.database.converters.UserConverter
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.storage.StorageInfo


@Entity(tableName = "users")
data class UserDbEntity(
	@PrimaryKey(autoGenerate = false)
	val id: Int,
	@TypeConverters(UserConverter::class)
	val user: UserModel,
	@TypeConverters(StorageInfoConverter::class)
	val metadata: StorageInfo
)
