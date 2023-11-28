package com.example.my_ecomerge_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.model.RecommendedFood


class AdapterRecommended(private var recommendedList: MutableList<RecommendedFood>, private val context: Context) :
    RecyclerView.Adapter<AdapterRecommended.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return recommendedList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recommended = recommendedList[position]
        Glide.with(context)
            .load(recommended.imgRecommended)
            .placeholder(R.drawable.pizza)
            .into(holder.imgRecommended)
        holder.tvItem.text = recommended.nameFood
        holder.tvPrice.text = recommended.price.toString()


    }

    inner class MyViewHolder( itemView: View ): RecyclerView.ViewHolder(itemView) {
        val imgRecommended: ImageView = itemView.findViewById(R.id.imgFood)
        val tvItem: TextView = itemView.findViewById(R.id.tvFood)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

    }
}