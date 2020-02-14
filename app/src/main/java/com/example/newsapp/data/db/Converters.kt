package com.example.newsapp.data.db

import androidx.room.TypeConverter
import com.example.newsapp.models.Article
import com.example.newsapp.models.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class Converters {
    @TypeConverter
    fun fromString(value: String): Any {
        val listType: Type =
            object : TypeToken<Any>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: Any): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromSource(value: String): Source {
        val listType: Type =
            object : TypeToken<Source>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toSource(list: Source): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Article> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType =
            object : TypeToken<List<Article>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Article>): String? {
        return gson.toJson(someObjects)
    }
}