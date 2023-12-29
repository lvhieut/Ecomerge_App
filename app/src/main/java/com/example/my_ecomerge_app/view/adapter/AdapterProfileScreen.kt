package com.example.my_ecomerge_app.view.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.model.Features_Profile_Screen
import com.example.my_ecomerge_app.view.activity.HomeAcitivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AdapterProfileScreen(private var listFeatures: MutableList<Features_Profile_Screen>, private var context: Context) :
    RecyclerView.Adapter<AdapterProfileScreen.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_screec_profile, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFeatures.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val feartues = listFeatures[position]
        holder.tvItem.text = feartues.nameFeatures
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tvDescriptionFeatures)

    }

}