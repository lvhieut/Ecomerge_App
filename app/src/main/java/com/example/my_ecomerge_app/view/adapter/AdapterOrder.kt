package com.example.my_ecomerge_app.view.adapter
import android.annotation.SuppressLint
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
import kotlin.math.min


class AdapterOrder(private var listOrder : MutableList<Order>, private var context: Context, private val itemCountToShow: Int):
    RecyclerView.Adapter<AdapterOrder.MyViewHolder>() {

    private var listener: OnItemClick? = null

    fun setOnItemClick(listener : OnItemClick){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_recommended, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return min(listOrder.size, itemCountToShow)
    }

    @SuppressLint("Recycle", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listOrderFood = listOrder[position]
        Log.d("---","${listOrder[position]}")

        val formattedPrice = formatPrice(listOrderFood.price)

        val imageUri = Uri.parse(listOrderFood.imgFood)
        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .placeholder(R.drawable.pho_icon)
            .into(holder.imgRecommended)
        holder.tvItem.text = listOrderFood.nameFood
        holder.tvPrice.text = formattedPrice

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        val imgRecommended: ImageView = itemView.findViewById(R.id.imgFoodRecommended)
        val tvItem: TextView = itemView.findViewById(R.id.tvFoodRcm)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    private fun formatPrice(price: Double): String {
        val vietnamLocale = Locale("vi", "VN")
        val currencyFormat = NumberFormat.getCurrencyInstance(vietnamLocale)
        currencyFormat.currency = Currency.getInstance("VND")
        return currencyFormat.format(price)
    }


}