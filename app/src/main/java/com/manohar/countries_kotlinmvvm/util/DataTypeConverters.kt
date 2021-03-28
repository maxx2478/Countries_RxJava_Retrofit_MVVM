package com.manohar.countries_kotlinmvvm.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manohar.countries_kotlinmvvm.model.Country
import com.manohar.countries_kotlinmvvm.model.Languages
import java.lang.reflect.Type
import java.util.*




class DataTypeConverters {

    var gson = Gson()

    @TypeConverter
    fun toList(strings: String): List<String> {
        val list = mutableListOf<String>()
        val array = strings.split(",")
        for (s in array) {
            list.add(s)
        }
        return list
    }

    @TypeConverter
    fun toString(strings: List<String>): String {
        var result = ""
        strings.forEachIndexed { index, element ->
            result += element
            if(index != (strings.size-1)){
                result += ","
            }
        }
        return result
    }



        @TypeConverter
        fun stringToArrayList(data: String?): List<Country> {
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type = object : TypeToken<List<Country?>?>() {}.getType()
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObjects: List<Country?>?): String {
            return gson.toJson(someObjects)
        }

    @TypeConverter
    fun fromString(value: String?): List<Languages> {
        val mapType = object : TypeToken<List<Languages?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(list: List<Languages?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }




}