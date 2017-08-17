package com.karntrehan.nagar.cities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.karntrehan.nagar.cities.repo.CitiesRepository
import com.karntrehan.nagar.NagarApplication
import com.karntrehan.nagar.cities.entities.CityEntity
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

    val loadStatus: LiveData<Boolean> = Transformations.switchMap(lastPosition)
    { position ->
        citiesRepository.getLoadingStatus(position)
    }

    val errorStatus : LiveData<String> = Transformations.switchMap(lastPosition)
    { position ->
        citiesRepository.getErrorStatus(position)
    }


    fun setInput(position: Int) {
        lastPosition.value = position
    }

    fun refreshDb() {
        citiesRepository.deleteCities()
        lastPosition.value = 0
    }
}