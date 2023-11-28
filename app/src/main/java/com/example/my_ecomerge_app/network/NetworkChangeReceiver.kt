package com.example.my_ecomerge_app.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkChangeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork == null || !activeNetwork.isConnected) {
            // Mạng bị tắt, thực hiện xử lý tại đây
            Toast.makeText(context, "Mạng đã bị tắt", Toast.LENGTH_SHORT).show()
        } else {
            // Mạng đã kết nối, thực hiện xử lý tại đây
            Toast.makeText(context, "Mạng đã kết nối", Toast.LENGTH_SHORT).show()
        }
    }
}