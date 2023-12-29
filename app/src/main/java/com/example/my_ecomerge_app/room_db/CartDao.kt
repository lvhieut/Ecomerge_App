package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.Cart

@Dao
interface CartDao {
    @Insert
    suspend fun insertItem(cartItems: Cart)

    @Delete
    suspend fun deleteItem(cartItems : Cart)

    @Update
    suspend fun updateItem(cartItems: Cart)

    @Query("DELETE FROM Cart WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)


    @Query("SELECT * FROM Cart")
    suspend fun getAllCart(): List<Cart>

    @Query("SELECT nameFood FROM Cart")
    suspend fun getAllNameCart() : List<String>
}