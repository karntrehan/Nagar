package com.karntrehan.nagar.cities

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.karntrehan.nagar.R
import com.karntrehan.nagar.databinding.ActivityCitiesBinding
import com.karntrehan.nagar.cities.viewmodel.CitiesViewModel


class CitiesActivity : LifecycleActivity() {

    lateinit var binding: ActivityCitiesBinding

    val citiesViewModel: CitiesViewModel by lazy {
        ViewModelProviders.of(this).get(CitiesViewModel::class.java)
    }

    internal val citiesAdapter: CitiesAdapter by lazy { CitiesAdapter(context) }

    val context: Context by lazy { this }

    val layoutManager: LinearLayoutManager by lazy {
        binding.rvCities.layoutManager as LinearLayoutManager
    }

    var canLoadMore = true

    val TAG = "CitiesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cities)
        binding.rvCities.setHasFixedSize(true)

        binding.rvCities.adapter = citiesAdapter

        binding.rvCities.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (citiesAdapter.itemCount > 1 && lastPosition == citiesAdapter.itemCount - 1
                        && canLoadMore) {
                    Log.d(TAG, "Load more $lastPosition")
                    citiesViewModel.setInput(lastPosition)
                    canLoadMore = false
                }
            }
        })

        citiesViewModel.cities.observe(this, Observer { citiesList ->
            val lastVisi = layoutManager.findLastVisibleItemPosition()

            hideSRLLoader()

            Log.d(TAG, "Got cities: $citiesList")
            if (citiesList != null && citiesList.isNotEmpty()) {
                canLoadMore = true
                citiesAdapter.addItems(if (lastVisi < 0) 0 else lastVisi, citiesList)
            }
        })

        citiesViewModel.loadStatus.observe(this, Observer { loading ->
            Log.d(TAG, "Loading $loading")
            if (!loading!!)
                citiesAdapter.removeLoader()
        })


        citiesViewModel.errorStatus.observe(this, Observer { errorStatus ->
            citiesAdapter.removeLoader()
            hideSRLLoader()
            Toast.makeText(context, errorStatus, Toast.LENGTH_LONG).show()
        })

        binding.srlCities.setOnRefreshListener({
            Log.d(TAG, "Refresh")
            citiesAdapter.clearAll()
            citiesViewModel.refreshDb()
        })

    }

    private fun hideSRLLoader() {
        if (binding.srlCities.isRefreshing)
            binding.srlCities.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        citiesViewModel.setInput(0)
    }
}
