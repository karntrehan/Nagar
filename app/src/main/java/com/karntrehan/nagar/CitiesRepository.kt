package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
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
        val citiesService: CitiesService,
        val preferences: SharedPreferences
) : CitiesContract.Repository {

    val TAG = "CitiesRepo"
    val LIMIT = 10

    override fun getCities(offset: Int): LiveData<List<CityEntity>> {
        Log.d(TAG, "Get cities O:$offset")

        if (preferences.getBoolean(Constants.MORE_REMOTE_CITIES, true))
            getRemoteCities(offset, LIMIT)

        val result: LiveData<List<CityEntity>> = cityDao.loadLocalCities(offset, LIMIT)
        return result
    }

    private fun getRemoteCities(offset: Int, limit: Int) {
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
                        preferences
                                .edit()
                                .putBoolean(Constants.MORE_REMOTE_CITIES,
                                        citiesResponse.cities.isEmpty())
                                .apply()
                    }).start()
                }
            }

            override fun onFailure(call: Call<CitiesResponse>?, t: Throwable?) {
                Log.d(TAG, "Failure: " + t?.localizedMessage)
            }

        })
    }
}