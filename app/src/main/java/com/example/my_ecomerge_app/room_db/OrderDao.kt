package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.Order
@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM `order`")
    suspend fun getAllItems(): List<Order>

    @Query("SELECT * FROM `order` WHERE nameFood LIKE '%' || :searchText || '%'")
    suspend fun searchItems(searchText: String?): List<Order?>?

    @Query("SELECT * FROM `order` WHERE type = 'Hamburger'")
    suspend fun getAllBurgersType(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Chicken'")
    suspend fun getAllFriedChickenType(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Coffee'")
    suspend fun getAllCoffee(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Salad'")
    suspend fun getAllSalad(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Noodle'")
    suspend fun getAllNoodle(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Sausage'")
    suspend fun getAllSausage(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Tea'")
    suspend fun getAllTea(): List<Order>

    @Query("SELECT * FROM `order` WHERE type = 'Drink'")
    suspend fun getAllDrink(): List<Order>
}