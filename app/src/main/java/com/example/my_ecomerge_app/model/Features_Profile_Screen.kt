package com.example.my_ecomerge_app.model

import androidx.room.PrimaryKey

class Features_Profile_Screen(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameFeatures : String,
) {
    constructor(nameFeatures: String) : this(0, nameFeatures )
}