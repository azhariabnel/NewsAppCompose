package com.example.servicedata.remote

import com.example.servicedata.Result
import com.example.servicedata.model.TopHeaderResponse

interface BaseRepository {

    suspend fun getTopHeadlines(country: String, apiKey: String) : Result<TopHeaderResponse>
}