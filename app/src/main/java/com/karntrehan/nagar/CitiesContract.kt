package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import com.karntrehan.nagar.data.entities.CityEntity

/**
 * Created by karn on 14-08-2017.
 */
interface CitiesContract {
    interface Repository{
        fun getCities(offset: Int,limit: Int = 10) : LiveData<List<CityEntity>>
        fun saveCities(cities: List<CityEntity>)
    }
}