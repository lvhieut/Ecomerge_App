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
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.view.activity.HomeAcitivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AdapterBill(private var listProduct: MutableList<Cart>, private var context: Context) :
    RecyclerView.Adapter<AdapterBill.MyViewHolder>() {

//    private var listener: OnItemClick? = null
//
//    fun setOnItemClick(listener : OnItemClick){
//        this.listener = listener
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bill_order, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listProduct[position]
        val imageUri = Uri.parse(product.imgFood)
        Log.d("---", imageUri.toString())
        val formattedPrice = formatPrice(product.price)
        val updatedTotalPrice = calculateTotalPrice()

        holder.tvNameFood.text = product.nameFood
        holder.tvPrice.text = formattedPrice
        holder.tvQuantity.text = product.quantity.toString()
//        holder.tvPrice.text = updatedTotalPrice

        var quantity = product.quantity
        var calculate: Double = 1.00

        holder.btnPlus.setOnClickListener {
            quantity++
            holder.tvQuantity.text = quantity.toString()
            calculate = quantity * product.price

            product.quantity = quantity
//            product.price = calculate

            CoroutineScope(Dispatchers.IO).launch {
                MyApplication.appDatabase.cartDao().updateItem(product)
            }
        }
        holder.btnMinus.setOnClickListener {
            quantity--
            val currentQuantity = quantity
            holder.tvQuantity.text = quantity.toString()

            product.quantity = quantity
            val updatedTotalPrice = calculateTotalPrice()
//            listCart.price = calculate
            CoroutineScope(Dispatchers.IO).launch {
                MyApplication.appDatabase.cartDao().updateItem(product)
            }
            if (quantity == 0) {
                AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setIcon(R.drawable.baseline_warning_24)
                    .setMessage("Bạn có muốn xoá sản phẩm này không?")
                    .setPositiveButton("Có") { dialog, _ ->
                        // Xoá phần tử khỏi danh sách và cập nhật UI
                        listProduct.removeAt(position)
                        notifyDataSetChanged()
                        // Xoá phần tử từ cơ sở dữ liệu
                        CoroutineScope(Dispatchers.IO).launch {
                            MyApplication.appDatabase.cartDao().deleteItemById(product.id)
                        }
                        if (listProduct.size <= 0) {
                            val intent = Intent(context, HomeAcitivity::class.java)
                            context.startActivity(intent)
                            (context as AppCompatActivity).finish()
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("Không") { dialog, _ ->
                        val setQuantity = "1"
                        holder.tvQuantity.text = setQuantity
                        val updatedTotalPrice = calculateTotalPrice()
                        holder.tvPrice.text = updatedTotalPrice
                        CoroutineScope(Dispatchers.IO).launch {
                            product.quantity = setQuantity.toInt()
                            MyApplication.appDatabase.cartDao().updateItem(product)
                        }
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

    }

    fun calculateTotalPrice(): String {
        return formatPrice(listProduct.sumByDouble { it.price * it.quantity })

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameFood: TextView = itemView.findViewById(R.id.tvName)
        val tvChange: TextView = itemView.findViewById(R.id.tvChangeBill)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPriceBill)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuanityBill)
        val btnPlus: ImageView = itemView.findViewById(R.id.btnPlusBill)
        val btnMinus: ImageView = itemView.findViewById(R.id.btnMinusBill)
    }

    private fun formatPrice(price: Double): String {
        val vietnamLocale = Locale("vi", "VN")
        val currencyFormat = NumberFormat.getCurrencyInstance(vietnamLocale)
        currencyFormat.currency = Currency.getInstance("VND")
        return currencyFormat.format(price)
    }
}