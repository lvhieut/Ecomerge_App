package com.example.my_ecomerge_app.room_db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.model.Categories
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.model.RecommendedFood
import com.example.my_ecomerge_app.model.SearchHistory

@Database(entities = [
    Categories::class,
    Order::class,
    RecommendedFood::class,
    SearchHistory::class,
    Cart::class],
    version = 4,
    autoMigrations = [AutoMigration(
        from = 2, to =  4
    )]
)

abstract class AppDatabae : RoomDatabase() {
    abstract fun categoriesDao() : CategoriesDAO
    abstract fun recommendedDao(): RecommendedDao
    abstract fun orderDao(): OrderDao
    abstract fun searchHistoryDao() : SearchHistoryDao
    abstract fun cartDao(): CartDao
}