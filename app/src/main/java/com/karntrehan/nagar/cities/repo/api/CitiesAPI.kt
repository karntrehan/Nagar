package com.karntrehan.nagar.cities.repo.api

import com.karntrehan.nagar.util.Constants
import com.karntrehan.nagar.cities.entities.CitiesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karn on 14-08-2017.
 */
interface CitiesAPI {

    @GET(Constants.CITIES_API)
    fun getCities(@Query("limit") limit: Int = 10, @Query("offset") offset: Int): Call<CitiesResponse>
}