package com.example.boredApplication.Repository

import androidx.lifecycle.LiveData
import com.example.boredApplication.Data.db.ActivityDao
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.Network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

/**
 * Class to bind between the fetch of the data and the ViewModel
 */
class HomeRepository@Inject constructor(private val activityDao: ActivityDao, private val activityApi: RetrofitService) {

    // Get activity from the api
     suspend fun getActivity(): ActivityEntity? {
         var listActivities:  ActivityEntity? = null

         CoroutineScope(Dispatchers.IO).async {
             val response = activityApi.getActivity()

             listActivities = if (response != null) {
                 response.body()!!
             } else {
                 null
             }
         }.await()
        return  listActivities
    }

    suspend fun insert(activity: ActivityEntity) {
        activityDao.insertActivity(activity)
    }

    fun getAllFromRoom(): LiveData<List<ActivityEntity>> {
        return activityDao.getAll()
    }

    suspend fun deleteAll(){
        activityDao.deleteAll()
    }
}