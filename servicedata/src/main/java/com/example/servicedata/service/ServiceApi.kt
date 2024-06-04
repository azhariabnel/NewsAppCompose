package com.example.servicedata.service

import com.example.servicedata.model.TopHeaderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String, @Query("apiKey") apiKey: String): TopHeaderResponse
}