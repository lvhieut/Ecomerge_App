package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.DeliveryStatus

@Dao
interface DeliveryStatusDao {
    @Insert
    suspend fun insert(deliveryStatus: DeliveryStatus)

    @Update
    suspend fun update(deliveryStatus: DeliveryStatus)

    @Delete
    suspend fun delete(deliveryStatus: DeliveryStatus)

    @Query("SELECT * FROM DeliveryStatus")
    suspend fun getAllDeliveryStatus(): List<DeliveryStatus>
}