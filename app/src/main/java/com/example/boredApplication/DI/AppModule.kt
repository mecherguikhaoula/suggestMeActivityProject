package com.example.boredApplication.DI

import android.content.Context
import com.example.boredApplication.Data.db.AppDatabase
import com.example.boredApplication.Data.db.ActivityDao
import com.example.boredApplication.Network.RetrofitBuilder
import com.example.boredApplication.Network.RetrofitService
import com.example.boredApplication.Repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Object for dependency injection
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRepository(activityDao: ActivityDao, retrofitService: RetrofitService): HomeRepository {
        return HomeRepository(activityDao, retrofitService)
    }

    @Singleton
    @Provides
    fun provideSuggestActivityApi(): RetrofitService{
        return RetrofitBuilder().createRetrofitInstance().create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ActivityDao {
        return AppDatabase.getDatabase(context).getActivityDao()
    }
}