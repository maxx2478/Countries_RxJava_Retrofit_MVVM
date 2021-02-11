package com.manohar.countries_kotlinmvvm.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryname: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flagPNG: String?
) {

}