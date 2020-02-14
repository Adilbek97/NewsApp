package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class Source(
    @PrimaryKey
    var name: String = "",
    var id: Any? = null)