package com.berglund.qapital.database.converters

import androidx.room.TypeConverter
import com.berglund.qapital.models.UserModel
import com.google.gson.Gson

class UserConverter {

	@TypeConverter
	fun toUser(json: String): UserModel = Gson().fromJson(json, UserModel::class.java)

	@TypeConverter
	fun fromUser(user: UserModel): String = Gson().toJson(user, UserModel::class.java)
}
