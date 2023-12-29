package com.example.my_ecomerge_app.room_db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.model.Categories
import com.example.my_ecomerge_app.model.DeliveryStatus
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.model.RecommendedFood
import com.example.my_ecomerge_app.model.SearchHistory

@Database(entities = [
    Categories::class,
    Order::class,
    RecommendedFood::class,
    SearchHistory::class,
    Cart::class,
    DeliveryStatus::class],
    version = 1
//    autoMigrations = [AutoMigration(
//        from = 4, to =  6)]
)

abstract class AppDatabae : RoomDatabase() {
    abstract fun categoriesDao() : CategoriesDAO
    abstract fun recommendedDao(): RecommendedDao
    abstract fun orderDao(): OrderDao
    abstract fun searchHistoryDao() : SearchHistoryDao
    abstract fun cartDao(): CartDao

    abstract fun deliveryStatusDao() : DeliveryStatusDao
}