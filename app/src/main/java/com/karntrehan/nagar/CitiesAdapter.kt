package com.karntrehan.nagar

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.karntrehan.nagar.data.entities.CityEntity
import com.karntrehan.nagar.data.entities.LoadingEntity
import com.karntrehan.nagar.databinding.RvCityItemBinding
import com.karntrehan.nagar.databinding.RvLoadingItemBinding


/**
 * Created by karn on 14-08-2017.
 */
class CitiesAdapter(private val context: Context
                    , val items: ArrayList<Any> = ArrayList<Any>())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CITY = 0
    private val LOADING = 1

    init {
        addLoadingView()
    }


    //private val logger = KotlinLogging.logger {}
    val TAG = "CitiesAdapter"

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return this.items.size
    }

    fun addItems(lastPosition: Int, cities: List<CityEntity>) {
        items.addAll(lastPosition, cities)
        Log.d(TAG, "List ${this.items}")
        notifyItemRangeChanged(lastPosition, items.size)
    }

    fun removeLoader() {
        val toRemove = items.size - 1
        if (items[toRemove] is LoadingEntity) {
            items.removeAt(toRemove)
            notifyItemRemoved(toRemove)
        }
    }

    fun addLoadingView() {
        items.add(LoadingEntity())
    }


    fun clearAll() {
        items.clear()
        addLoadingView()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is CityEntity)
            return CITY
        else
            return LOADING
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            CITY -> {
                val v1: RvCityItemBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(viewGroup.context),
                        R.layout.rv_city_item, viewGroup, false)
                viewHolder = CityHolder(v1)
            }
            else -> {
                val v: RvLoadingItemBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(viewGroup.context),
                        R.layout.rv_loading_item, viewGroup, false)
                viewHolder = LoadingHolder(v)
            }
        }
        return viewHolder

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            CITY -> {
                val vh1 = viewHolder as CityHolder
                configureCity(vh1, position)
            }
            else -> {
                val vh = viewHolder as LoadingHolder
                configureLoading(vh)
            }
        }
    }

    private fun configureLoading(vh: LoadingHolder) {
        vh.binding.pbLoading.visibility = View.VISIBLE
    }

    private fun configureCity(vh1: CityHolder, position: Int) {
        val cityEntity = items[position] as CityEntity
        vh1.binding.tvCityName.text = cityEntity.name
        vh1.binding.llCity.setOnClickListener({
            Toast.makeText(context, "Selection is - " + cityEntity.name, Toast.LENGTH_LONG).show()
        })
    }

    class CityHolder(val binding: RvCityItemBinding) : RecyclerView.ViewHolder(binding.root)
    class LoadingHolder(val binding: RvLoadingItemBinding) : RecyclerView.ViewHolder(binding.root)


}