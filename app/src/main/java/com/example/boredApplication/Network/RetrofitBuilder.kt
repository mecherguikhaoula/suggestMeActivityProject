package com.example.boredApplication.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class RetrofitBuilder {

    /**
     * Create an instance of retrofit to the base URL
     */
    fun createRetrofitInstance() : Retrofit {
        val gson = GsonBuilder().setLenient().create()

        retrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofitInstance
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private var baseUrl: String = "https://www.boredapi.com/api/"
    private lateinit var retrofitInstance: Retrofit
}