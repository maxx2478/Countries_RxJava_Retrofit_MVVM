package com.manohar.countries_kotlinmvvm.util

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manohar.countries_kotlinmvvm.model.Country

@Database (entities = [(Country::class)],version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun countryDao(): CountryDAO
}
