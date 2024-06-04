package com.example.servicedata.di

import android.content.Context
import com.example.servicedata.Utilities
import com.example.servicedata.remote.BaseDefaultRepository
import com.example.servicedata.remote.BaseRepository
import com.example.servicedata.service.ServiceApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ServiceApiRuntime

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context)=
        Interceptor { chain ->
            val builder = chain.request().newBuilder()
            Utilities.getApiKey(context)?.let {
                builder.header("Authorization", "Bearer $it")
            }

            return@Interceptor chain.proceed(builder.build())
        }

    @Provides
    fun provideBaseUrl() =
        "https://newsapi.org/"

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            client(okHttpClient)
        }.build()

    @Singleton
    @ServiceApiRuntime
    @Provides
    fun provideServiceApi(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)

}

@Module
@InstallIn(ViewModelComponent::class)
object FeatureModule{

    @ViewModelScoped
    @Provides
    fun provideAccountRepository(@ApplicationContext context: Context,
                                 @AppModule.ServiceApiRuntime api2: ServiceApi) : BaseRepository {
        return BaseDefaultRepository(context, api2)

    }
}