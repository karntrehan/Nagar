package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.karntrehan.nagar.CitiesRepository
import com.karntrehan.nagar.NagarApplication
import com.karntrehan.nagar.data.entities.CityEntity
import javax.inject.Inject

class CitiesViewModel : ViewModel() {
    @Inject lateinit var citiesRepository: CitiesRepository
    private val lastPosition = MutableLiveData<Int>()

    init {
        NagarApplication.appComponent.inject(this)
    }

    val TAG = "CitiesViewModel"

    val cities: LiveData<List<CityEntity>> = Transformations.switchMap(lastPosition)
    { position ->
        Log.d(TAG,"SwitchMap : $position")
        citiesRepository.getCities(offset = position) }

    /*//FIXME
    fun saveTemp()
    {
        val citiesAL = ArrayList<CityEntity>()
        (1..100).mapTo(citiesAL) { CityEntity(it, "City $it", "Slug $it") }
        Log.d(TAG,citiesAL.toString())
        citiesRepository.saveCities(citiesAL)
    }*/

    fun setInput(position: Int) {
        lastPosition.value = position
    }


}