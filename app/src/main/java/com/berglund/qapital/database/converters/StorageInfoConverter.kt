package com.berglund.qapital.database.converters

import androidx.room.TypeConverter
import com.berglund.qapital.storage.StorageInfo
import com.google.gson.Gson

class StorageInfoConverter {

	@TypeConverter
	fun toStorageInfo(json: String): StorageInfo = Gson().fromJson(json, StorageInfo::class.java)

	@TypeConverter
	fun fromStorageInfo(storageInfo: StorageInfo): String = Gson().toJson(storageInfo, StorageInfo::class.java)
}
