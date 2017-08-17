package com.karntrehan.nagar.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.karntrehan.nagar.BuildConfig
import com.karntrehan.nagar.cities.repo.api.CitiesAPI
import com.karntrehan.nagar.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by karn on 13-08-2017.
 */
@Module(includes = arrayOf(AppModule::class))
class NetworkModule {

    @Provides
    @Singleton
    fun retrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient) =
            Retrofit.Builder().baseUrl(Constants.API_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build()

    @Provides
    @Singleton
    fun gsonConverterFactory() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun okhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        //Add StethoInterceptor only when app is in debug mode
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(StethoInterceptor())
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun cache(context: Context) =
            Cache(context.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun citiesService(retrofit: Retrofit): CitiesAPI =
            retrofit.create(CitiesAPI::class.java)
}

