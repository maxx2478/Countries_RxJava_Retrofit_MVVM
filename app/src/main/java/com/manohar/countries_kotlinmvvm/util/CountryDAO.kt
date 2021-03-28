package com.manohar.countries_kotlinmvvm.util

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manohar.countries_kotlinmvvm.model.Country


@Dao
interface CountryDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBooks(list: List<Country>)

    @Query(value = "Select * from Country")
    fun getAllBooks() : List<Country>
}