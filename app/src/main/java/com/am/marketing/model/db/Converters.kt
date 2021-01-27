package com.am.marketing.model.db

import androidx.room.TypeConverter
import com.am.marketing.model.data.Campaign
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun toListOfStrings(field: String): List<Int> {
        return field.split(",").map{ it.toInt()}
    }
    @TypeConverter
    fun fromListOfStrings(field: List<Int>): String {
        return field.joinToString(",")
    }

    @TypeConverter
    fun fromStringToAny(field: String): Any {
        return field
    }
    @TypeConverter
    fun fromAnyToString(field: Any): String {
        return field.toString()
    }

    @TypeConverter
    fun fromCampaignsList(countryLang: List<Campaign?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Campaign?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCampaignList(countryLangString: String?): List<Campaign>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Campaign?>?>() {}.type
        return gson.fromJson<List<Campaign>>(countryLangString, type)
    }

}