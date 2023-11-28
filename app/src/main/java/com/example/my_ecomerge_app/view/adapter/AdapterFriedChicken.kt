package com.example.my_ecomerge_app.view.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AdapterFriedChicken(private var listChickenFried : MutableList<Order>, private var context: Context):  RecyclerView.Adapter<AdapterFriedChicken.MyViewHolder>() {

    private var listener: OnItemClick? = null

    fun setOnItemClick(listener : OnItemClick){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hamburger_screen, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listChickenFried.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listFriedChicken = listChickenFried[position]
        val imageUri = Uri.parse(listFriedChicken.imgFood)
        Log.d("---",imageUri.toString())
        val formattedPrice = formatPrice(listFriedChicken.price)

        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .placeholder(R.drawable.pho_icon)
            .into(holder.imgBurger)
        holder.tvItem.text = listFriedChicken.nameFood
        holder.tvPrice.text = formattedPrice
        holder.tvDate.text = listFriedChicken.date

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val imgBurger: ImageView = itemView.findViewById(R.id.imgCategoriesFood)
        val tvItem: TextView = itemView.findViewById(R.id.tvNameBurGer)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPricaBurger)
        val tvDate: TextView = itemView.findViewById(R.id.tvDateBurgerNum)
    }
    private fun formatPrice(price: Double): String {
        val vietnamLocale = Locale("vi", "VN")
        val currencyFormat = NumberFormat.getCurrencyInstance(vietnamLocale)
        currencyFormat.currency = Currency.getInstance("VND")
        return currencyFormat.format(price)
    }
}