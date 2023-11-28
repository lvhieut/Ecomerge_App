package com.example.my_ecomerge_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommended")
class RecommendedFood(
    @PrimaryKey(autoGenerate = true)
    val rcmId: Long,
    val imgRecommended: Int,
    val nameFood: String,
    val price: Long,
) {
    constructor(imgRecommended: Int, nameFood: String, price: Long): this(0, imgRecommended, nameFood, price)
}