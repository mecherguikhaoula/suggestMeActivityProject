package com.example.boredApplication.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.Repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel@Inject constructor(private val homeRepository: HomeRepository): ViewModel() {
    fun getActivity(): LiveData<ActivityEntity> {
        return activityFromApi
    }

    fun getActivityFromApi() {
        var responseCallApi: ActivityEntity

        isProgressingBar.value = true
        viewModelScope.launch(Main) {
            try {
                responseCallApi = homeRepository.getActivity()!!

                if (responseCallApi != null) {
                    activityFromApi.postValue(responseCallApi)
                } else {
                    activityFromApi.postValue(null)
                }
            } catch (e: Exception) {
                activityFromApi.postValue(null)
            }
            isProgressingBar.postValue(false)
        }
    }

    fun getIsProgressing(): LiveData<Boolean> {
        return isProgressingBar
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    var activityFromApi = MutableLiveData<ActivityEntity>()
    var isProgressingBar: MutableLiveData<Boolean> = MutableLiveData(false)
}