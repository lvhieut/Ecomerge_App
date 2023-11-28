package com.example.my_ecomerge_app.view.fragment.DetailFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.FragmentDetailBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.AddFoodFragment.AddFoodFragment
import java.text.NumberFormat
import java.util.Locale


class DetailFragment : Fragment() {

    private val detailBinding by lazy { FragmentDetailBinding.inflate(layoutInflater) }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedBundle = arguments
        if (receivedBundle != null){
            val order = receivedBundle.getParcelable<Order>("order")
                Log.d("order",order.toString())
                if (order != null){
                    val imgFood = order.imgFood
                    val nameFood = order.nameFood
                    val priceFood = order.price
                    val dateFood = order.date
                    val descriptionFood =order.describe
                    val formattedPrice = formatCurrency(priceFood.toString())

                    Log.d("123",imgFood)

                    Glide.with(requireContext())
                            .load(imgFood.toUri())
                            .placeholder(R.drawable.bac_xiu)
                        .error(R.drawable.pho)
                            .into(detailBinding.imgFood)

                    detailBinding.collapsingToolbar.title = nameFood
                    detailBinding.tvDescriptionFood.text = descriptionFood
                    detailBinding.tvPrice.text = formattedPrice
                    detailBinding.tvDate.text = dateFood
                }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailBinding.btnAddToCart.setOnClickListener {
            val bottomSheetFragment = AddFoodFragment()
            bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
        }
    }

    private fun formatCurrency(priceFood: String): String {
        try {
            val amount = priceFood.toDouble()
            val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            return format.format(amount)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return priceFood // Trả về giá trị ban đầu nếu có lỗi
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return detailBinding.root
    }


}