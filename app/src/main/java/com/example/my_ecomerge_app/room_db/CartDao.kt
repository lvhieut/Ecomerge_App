package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.my_ecomerge_app.model.Cart

@Dao
interface CartDao {
    @Insert
    suspend fun insertItem(cartItems: Cart)

    @Delete
    suspend fun deleteItem(cartItems : Cart)

    @Query("SELECT * FROM Cart")
    suspend fun getAllCart(): List<Cart>
}