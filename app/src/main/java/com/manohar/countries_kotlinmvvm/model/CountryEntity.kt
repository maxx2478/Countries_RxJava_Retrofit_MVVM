package com.manohar.countries_kotlinmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
 data class Country(

    @SerializedName("name")
    @PrimaryKey
     val countryname: String = "",

    @SerializedName("capital")
    @ColumnInfo(name = "capital")
    val capital:  String = "",


    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    val flagPNG:  String = "",

    @SerializedName("region")
    @ColumnInfo(name = "region")
    val region:  String = "",

    @SerializedName("subregion")
    @ColumnInfo(name = "subregion")
    val subregion:  String = "",

    @SerializedName("population")
    @ColumnInfo(name = "population")
    val population:  Int = 0,

    @SerializedName("borders")
    @ColumnInfo(name = "borders")
    val borders:  List<String> = arrayListOf<String>(),

    @SerializedName("languages")
    @ColumnInfo(name = "languages")
    val languages: List<Languages> = arrayListOf<Languages>()

    )