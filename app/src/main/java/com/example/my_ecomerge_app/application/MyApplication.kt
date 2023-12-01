package com.example.my_ecomerge_app.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.my_ecomerge_app.room_db.AppDatabae
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyApplication: Application() {

    companion object{
        val CHANNEL_ID : String = "push_notification_id"
        lateinit var appDatabase: AppDatabae
    }

    override fun onCreate() {
        super.onCreate()
        createChannelNotification()


        appDatabase = Room.databaseBuilder(applicationContext, AppDatabae::class.java, "database")
            .build()

    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    val BASE_URL = "https://revgeocode.search.hereapi.com/v1"

    fun getIntance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}