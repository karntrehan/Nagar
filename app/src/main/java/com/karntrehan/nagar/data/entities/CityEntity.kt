package com.karntrehan.nagar.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by karn on 13-08-2017.
 */
@Entity(tableName = CityEntity.TABLE_NAME)
data class CityEntity(
        @PrimaryKey
        @SerializedName("id")
        val uuid: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
) {
    companion object {
        const val TABLE_NAME = "city"
    }
}