package com.karntrehan.nagar.cities.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by karn on 14-08-2017.
 */
data class CitiesResponse(
        @SerializedName("meta")
        val cMetaResponse: CitiesResponse.Meta,
        @SerializedName("objects")
        val cities: List<CityEntity>
) {
    data class Meta(
            @SerializedName("next")
            val nextUrl: String,
            @SerializedName("previous")
            val previousUrl: String,
            @SerializedName("count")
            val totalCount: Int,
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("offset")
            val offset: Int
    )
}