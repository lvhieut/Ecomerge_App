package com.example.my_ecomerge_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity(tableName = "DeliveryStatus")
data class DeliveryStatus(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cartId: Int,
    val deliveryStatus: String,
    val customerName: String,
    val orderTime: String,
    val orderDate: String,
    val foodName: String,
    val quantityFood: Int
) {
    companion object {
        fun fromCart(
            cart: Cart,
            deliveryStatus: String,
            customerName: String,
            orderTime: String,
            orderDate: String
        ): DeliveryStatus {
            return DeliveryStatus(
                cartId = cart.id,
                deliveryStatus = deliveryStatus,
                customerName = customerName,
                orderTime = orderTime,
                orderDate = orderDate,
                foodName = cart.nameFood,
                quantityFood = cart.quantity
            )
        }
    }

}