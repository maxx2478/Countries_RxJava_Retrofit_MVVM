package com.manohar.countries_kotlinmvvm.model

import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi
{
    @GET("rest/v2/region/asia")
    fun getCountries(): Single<List<Country>>

}