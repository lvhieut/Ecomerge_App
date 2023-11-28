package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.Categories


@Dao
interface CategoriesDAO  {

    @Insert
    suspend fun insertCategories(categories: Categories)

    @Update
    suspend fun updateCategories(categories: Categories)

    @Delete
    suspend fun deleteCategories(categories: Categories)

    @Query("SELECT * FROM Categories")
    suspend fun getAllItems(): List<Categories>

}