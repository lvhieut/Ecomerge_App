package com.example.my_ecomerge_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Categories(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var iconUri : Int
){
    constructor(name: String, iconUri: Int) : this(0, name, iconUri)
}





