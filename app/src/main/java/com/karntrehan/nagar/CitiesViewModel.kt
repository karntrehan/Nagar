package com.karntrehan.nagar

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
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
        citiesRepository.getCities(offset = position)
    }


    fun setInput(position: Int) {
        lastPosition.value = position
    }

    fun refreshDb() {
        citiesRepository.deleteCities()
        lastPosition.value = 0
    }
}