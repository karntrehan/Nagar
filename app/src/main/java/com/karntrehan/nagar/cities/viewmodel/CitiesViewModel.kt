package com.karntrehan.nagar.cities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.karntrehan.nagar.NagarApplication
import com.karntrehan.nagar.cities.entities.CityEntity
import com.karntrehan.nagar.cities.repo.CitiesRepository
import javax.inject.Inject

class CitiesViewModel : ViewModel() {
    @Inject lateinit var citiesRepository: CitiesRepository
    private val offsetLive = MutableLiveData<Int>()

    init {
        NagarApplication.appComponent.inject(this)
    }


    //Listing to changes on the offsetLive and trigger pulls from the repo
    val cities: LiveData<List<CityEntity>> = Transformations.switchMap(offsetLive)
    { position ->
        citiesRepository.getCities(offset = position)
    }

    //Propagating changes from the repo to the activity to display loading
    val loadStatus: LiveData<Boolean> = Transformations.switchMap(offsetLive)
    { position ->
        citiesRepository.getLoadingStatus(position)
    }

    //Propagating the errorStatus from the repo to activity
    val errorStatus : LiveData<String> = Transformations.switchMap(offsetLive)
    { position ->
        citiesRepository.getErrorStatus(position)
    }


    fun setInput(position: Int) {
        offsetLive.value = position
    }

    fun refreshDb() {
        citiesRepository.deleteCities()
        offsetLive.value = 0
    }
}