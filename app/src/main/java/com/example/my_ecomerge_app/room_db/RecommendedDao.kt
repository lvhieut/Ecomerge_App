package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.Categories
import com.example.my_ecomerge_app.model.RecommendedFood


@Dao
interface RecommendedDao  {

    @Insert
    suspend fun insertRecommendFood(recommendedFood: RecommendedFood)

    @Update
    suspend fun updateCategories(recommendedFood: RecommendedFood)

    @Delete
    suspend fun deleteCategories(recommendedFood: RecommendedFood)



}