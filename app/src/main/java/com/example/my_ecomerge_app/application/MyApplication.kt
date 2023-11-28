package com.example.my_ecomerge_app.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.my_ecomerge_app.room_db.AppDatabae


class MyApplication: Application() {

    companion object{
        val CHANNEL_ID : String = "push_notification_id"
        lateinit var appDatabase: AppDatabae
    }

    override fun onCreate() {
        super.onCreate()
        createChannelNotification()

//        val MIGRATION_2_3 : Migration =  object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                        + "`name` TEXT, PRIMARY KEY(`id`))")
//            }
//        }
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabae::class.java, "database")
//            .addMigrations(MIGRATION_2_3)
            .build()

    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}