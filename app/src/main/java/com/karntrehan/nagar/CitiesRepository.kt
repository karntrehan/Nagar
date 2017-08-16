package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.karntrehan.nagar.data.CityDao
import com.karntrehan.nagar.data.entities.CitiesResponse
import com.karntrehan.nagar.data.entities.CityEntity
import dagger.Provides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by karn on 14-08-2017.
 */
@Singleton
class CitiesRepository @Inject constructor(
        val cityDao: CityDao,
        val citiesService: CitiesService
) : CitiesContract.Repository {

    val TAG = "CitiesRepo"

    override fun saveCities(cities: List<CityEntity>) {
        cityDao.insertAllCities(cities)
    }

    override fun getCities(offset: Int, limit: Int): LiveData<List<CityEntity>> {
        val result = cityDao.loadAllCities(offset, limit)

        Log.d(TAG, "From DB: " + result.value.toString())

        if (result.value?.size ?: 0 < 10)
            goRemoteCities(offset, limit)

        return result
    }

    private fun goRemoteCities(offset: Int, limit: Int) {
        Log.d(TAG, "getRemoteCities")

        val call: Call<CitiesResponse> = citiesService.getCities(limit, offset)
        call.enqueue(object : Callback<CitiesResponse> {
            override fun onResponse(call: Call<CitiesResponse>?, response: Response<CitiesResponse>?) {
                if (response!!.isSuccessful) {
                    val citiesResponse = response.body()
                    Log.d(TAG, "Success: " + citiesResponse.toString())



                    Thread(Runnable {
                        cityDao.insertAllCities(citiesResponse?.cities)
                    }).start()
                }
            }

            override fun onFailure(call: Call<CitiesResponse>?, t: Throwable?) {
                Log.d(TAG, "Failure: " + t?.localizedMessage)
            }

        })
    }
}