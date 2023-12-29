package com.example.my_ecomerge_app.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val searchKeyword: String,
)


