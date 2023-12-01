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
import com.example.my_ecomerge_app.model.Categories
import com.example.my_ecomerge_app.view.Interface.OnItemClickListener


class AdapterCategories(
    private var itemList: ArrayList<Categories>,
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterCategories.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_categories, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categories = itemList[position]
        Glide.with(context)
            .load(categories.iconUri)
            .placeholder(R.drawable.pizza)
            .into(holder.imgCategories)
        holder.tvItem.text = categories.name

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(categories)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCategories: ImageView = itemView.findViewById(R.id.imgImageItem)
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
    }
}