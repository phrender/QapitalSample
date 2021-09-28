package com.berglund.qapital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.berglund.qapital.database.converters.StorageInfoConverter
import com.berglund.qapital.database.converters.UserConverter
import com.berglund.qapital.database.entities.UserDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

	@Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
	abstract fun get(userId: Int): Flow<List<UserDbEntity>>

	@Query("DELETE FROM activities WHERE id = :key")
	abstract fun delete(key: Int)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	@TypeConverters(UserConverter::class, StorageInfoConverter::class)
	abstract fun insert(entity: UserDbEntity)
}
