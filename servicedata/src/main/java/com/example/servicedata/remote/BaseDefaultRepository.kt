package com.example.servicedata.remote

import android.content.Context
import com.example.servicedata.Result
import com.example.servicedata.di.AppModule
import com.example.servicedata.model.TopHeaderResponse
import com.example.servicedata.service.ServiceApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BaseDefaultRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @AppModule.ServiceApiRuntime val api: ServiceApi
) : BaseRepository {

    override suspend fun getTopHeadlines(
        country: String,
        apiKey: String
    ): Result<TopHeaderResponse> {
        return try {
           val result = api.getTopHeadlines(country,apiKey)
           Result.Success(result)
        } catch (ex : Exception){
            Result.Error(ex)
        }
    }

}