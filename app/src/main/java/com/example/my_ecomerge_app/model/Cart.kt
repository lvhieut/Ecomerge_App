package com.example.my_ecomerge_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imgFood: String,
    val nameFood: String,
    val price: Double,
    val describe: String,
    val date: String,
    val type: String,
    var quantity: Int = 1
){
    companion object {
        fun fromOrder(order: Order, quantity: Int = 1): Cart {
            return Cart(
                imgFood = order.imgFood,
                nameFood = order.nameFood,
                price = order.price,
                describe = order.describe,
                date = order.date,
                type = order.type,
                quantity = quantity
            )
        }
    }
}
