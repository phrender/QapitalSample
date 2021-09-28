package com.berglund.qapital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.berglund.qapital.database.converters.ActivityConverter
import com.berglund.qapital.database.converters.StorageInfoConverter
import com.berglund.qapital.database.entities.ActivitiesDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ActivityDao {

	@Query("SELECT * FROM activities WHERE id = :key LIMIT 1")
	abstract fun get(key: Int): Flow<List<ActivitiesDbEntity>>

	@Query("SELECT * FROM activities LIMIT 1")
	abstract fun getAll(): Flow<List<ActivitiesDbEntity>>

	@Query("DELETE FROM activities WHERE id = :key")
	abstract fun delete(key: Int)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	@TypeConverters(ActivityConverter::class, StorageInfoConverter::class)
	abstract fun insert(entity: ActivitiesDbEntity)
}
