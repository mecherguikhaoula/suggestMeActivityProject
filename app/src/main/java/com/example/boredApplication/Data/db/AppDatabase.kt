package com.example.boredApplication.Data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boredApplication.Data.Models.ActivityEntity

@Database(
    entities = [ActivityEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getActivityDao(): ActivityDao
    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return instance ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "activitiesDatabase.db"
                ).build()
                instance = instance
                // return instance
                instance
            }
        }
    }
}