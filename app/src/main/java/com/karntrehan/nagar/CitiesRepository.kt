package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import android.util.Log
import com.karntrehan.nagar.data.CityDao
import com.karntrehan.nagar.data.entities.CitiesResponse
import com.karntrehan.nagar.data.entities.CityEntity
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
    var hasLoadedAll: Boolean = false

    override fun getCities(offset: Int): LiveData<List<CityEntity>> {
        Log.d(TAG, "Get cities O:$offset")

        val result: LiveData<List<CityEntity>> = cityDao.loadLocalCities(offset, Constants.LIMIT)

        return result
    }

    override fun getRemoteCities(offset: Int, limit: Int) {
        Log.d(TAG, "getRemoteCities $offset")

        val call: Call<CitiesResponse> = citiesService.getCities(limit, offset)
        call.enqueue(object : Callback<CitiesResponse> {
            override fun onResponse(call: Call<CitiesResponse>?, response: Response<CitiesResponse>?) {
                if (response!!.isSuccessful) {
                    val citiesResponse = response.body()
                    Log.d(TAG, "Success: " + citiesResponse.toString())

                    if (citiesResponse == null)
                        return

                    Thread(Runnable {
                        cityDao.insertAllCities(citiesResponse.cities)
                        hasLoadedAll = citiesResponse.cities.isEmpty()
                    }).start()
                }
            }

            override fun onFailure(call: Call<CitiesResponse>?, t: Throwable?) {
                Log.d(TAG, "Failure: " + t?.localizedMessage)
            }

        })
    }
}