package com.example.boredApplication.Network

import com.example.boredApplication.Data.Models.ActivityEntity
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("activity")
    suspend fun getActivity(): Response<ActivityEntity>
}