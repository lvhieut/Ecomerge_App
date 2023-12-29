package com.example.my_ecomerge_app.view.adapter

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.AddFoodFragment.AddFoodFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AdapterBurger(private var listBurger : MutableList<Order>, private var context: Context): RecyclerView.Adapter<AdapterBurger.MyViewHolder>() {

    private var listener: OnItemClick? = null

    fun setOnItemClick(listener : OnItemClick){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hamburger_screen, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBurger.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listHamBurger = listBurger[position]
        val imageUri = Uri.parse(listHamBurger.imgFood)
        Log.d("---",imageUri.toString())
        val formattedPrice = formatPrice(listHamBurger.price)

        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .placeholder(R.drawable.pho_icon)
            .into(holder.imgBurger)
        holder.tvItem.text = listHamBurger.nameFood
        holder.tvPrice.text = formattedPrice
        holder.tvDate.text = listHamBurger.date

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
        holder.btnAddBurger.setOnClickListener {
            val bottomSheetFragment = AddFoodFragment()
            val bundel = Bundle()
            bundel.putParcelable("thisOrder",listBurger[position])
            bottomSheetFragment.arguments = bundel
            bottomSheetFragment.show((context as AppCompatActivity).supportFragmentManager, bottomSheetFragment.tag)
        }

    }

//    private fun addItemToDatabase(order: Order) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val existingOrder = MyApplication.appDatabase.orderDao().getOrderById(order.id)
//
//            if (existingOrder != null) {
//                // Đối tượng đã tồn tại, có thể thực hiện các thao tác cập nhật hoặc xử lý khác
//            } else {
//                MyApplication.appDatabase.orderDao().insertOrder(order)
//            }
//        }
//    }


    inner class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)  {
        val imgBurger: ImageView = itemView.findViewById(R.id.imgCategoriesFood)
        val tvItem: TextView = itemView.findViewById(R.id.tvNameBurGer)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPricaBurger)
        val tvDate: TextView = itemView.findViewById(R.id.tvDateBurgerNum)
        val btnAddBurger : Button = itemView.findViewById(R.id.btnAddToCartBurger)
    }
    private fun formatPrice(price: Double): String {
        val vietnamLocale = Locale("vi", "VN")
        val currencyFormat = NumberFormat.getCurrencyInstance(vietnamLocale)
        currencyFormat.currency = Currency.getInstance("VND")
        return currencyFormat.format(price)
    }
}