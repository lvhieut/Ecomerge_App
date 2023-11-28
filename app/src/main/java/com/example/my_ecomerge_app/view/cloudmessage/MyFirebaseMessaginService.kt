package com.example.my_ecomerge_app.view.cloudmessage
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.view.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FbMessaginService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification: RemoteMessage.Notification? = message.notification

        if(notification == null){
            return
        }

        val strTitle: String? = notification.title
        val strMessage: String? = notification.body

        sendNotification(strTitle, strMessage)
    }

    private fun sendNotification(strTitle: String?, strMessage: String?) {

        val intent : Intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)


        val notificationBuilder = NotificationCompat.Builder(this,MyApplication.CHANNEL_ID)
            .setContentTitle(strTitle)
            .setContentText(strMessage)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)


        val notification = notificationBuilder.build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.let {
            notificationManager.notify(1, notification)
        }
    }
}