package com.example.boredApplication.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.Repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel@Inject constructor(private val homeRepository: HomeRepository): ViewModel() {

    fun getListActivitiesFromDatabase() {
      listActivities = homeRepository.getAllFromRoom()
    }

    fun getListActivitiesSearched(): LiveData<List<ActivityEntity>> {
        return this.listActivities
    }

    fun insertActitivity(activityCartModel: ActivityEntity) {
        viewModelScope.launch {
            homeRepository.insert(activityCartModel)
        }
    }

    fun deleteActivities() {
        viewModelScope.launch {
            homeRepository.deleteAll()
        }
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var listActivities: LiveData<List<ActivityEntity>> = MutableLiveData()
}