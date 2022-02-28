package com.example.boredApplication.Data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Update
import com.example.boredApplication.Data.Models.ActivityEntity

/**
 * Offer abstract access to our app's database
 */
@Dao
interface ActivityDao {
    @Query("SELECT * FROM activitiesTable")
    fun getAll(): LiveData<List<ActivityEntity>>

    @Query("DELETE FROM activitiesTable")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: ActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Update
    suspend fun update(activity: ActivityEntity)
}