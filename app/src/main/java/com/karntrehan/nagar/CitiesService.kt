package com.karntrehan.nagar

import com.karntrehan.nagar.data.entities.CitiesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karn on 14-08-2017.
 */
interface CitiesService {

    @GET(Constants.CITIES_API)
    fun getCities(@Query("limit") limit: Int = 10, @Query("offset") offset: Int): Call<CitiesResponse>
}