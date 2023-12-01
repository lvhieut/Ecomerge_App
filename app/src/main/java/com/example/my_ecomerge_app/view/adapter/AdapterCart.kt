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
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.model.Cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AdapterCart(private var listCart: MutableList<Cart>, private var context: Context) :
    RecyclerView.Adapter<AdapterCart.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listCart = listCart[position]
        val imageUri = Uri.parse(listCart.imgFood)
        Log.d("---", imageUri.toString())
        val formattedPrice = formatPrice(listCart.price)

        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .placeholder(R.drawable.pho_icon)
            .into(holder.imgFood)
        holder.tvItem.text = listCart.nameFood
        holder.tvPrice.text = formattedPrice
        var quantity = listCart.quantity
        var getPrice = listCart.price * quantity.toDouble()
        holder.tvTotal.text = formatPrice(getPrice)

        holder.tvQuantity.text = quantity.toString()

        var calculate: Double = 1.00
        holder.btnPlus.setOnClickListener {


            quantity++
            holder.tvQuantity.text = quantity.toString()
            calculate = quantity * listCart.price
            holder.tvTotal.text = formatPrice(calculate)

        }
        holder.btnMinus.setOnClickListener {
            quantity--
            holder.tvQuantity.text = quantity.toString()
            if (quantity == 0) {

                CoroutineScope(Dispatchers.IO).launch {
                    MyApplication.appDatabase.cartDao().deleteItem(listCart)
//                    Log.d("delete",listCart.)
                }
            }

        }


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFood: ImageView = itemView.findViewById(R.id.img_giohang)
        val tvItem: TextView = itemView.findViewById(R.id.tv_ten_giohang)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_giatien_giohang)
        val tvTotal: TextView = itemView.findViewById(R.id.tv_total_giohang)
        val btnPlus: ImageView = itemView.findViewById(R.id.btn_plus_giohang)
        val btnMinus: ImageView = itemView.findViewById(R.id.btn_minus_giohang)
        val tvQuantity: TextView = itemView.findViewById(R.id.tv_number_giohang)
    }

    private fun formatPrice(price: Double): String {
        val vietnamLocale = Locale("vi", "VN")
        val currencyFormat = NumberFormat.getCurrencyInstance(vietnamLocale)
        currencyFormat.currency = Currency.getInstance("VND")
        return currencyFormat.format(price)
    }
}