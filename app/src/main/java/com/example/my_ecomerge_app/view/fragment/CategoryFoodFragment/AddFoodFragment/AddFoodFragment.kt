package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.AddFoodFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentAddFoodBinding
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.view.fragment.ShareVm
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale


class AddFoodFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentAddFoodBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var value = 1

        binding.btnIncrease.setOnClickListener{
            value++
            binding.tvQuanity.text = value.toString()
        }
            binding.btnAdd.setOnClickListener {
                sharedViewModel.getSharedData().observe(viewLifecycleOwner, Observer {
                    val order = it
                    val cartItem = Cart.fromOrder(order).apply {
                        this.quantity = value
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        MyApplication.appDatabase.cartDao().insertItem(cartItem)
                    }
                })
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getSharedData().observe(viewLifecycleOwner, Observer { order ->
            order.let {

                val price = order.price
                val formatCurrency= formatCurrency(priceFood = price.toString())
                Glide.with(requireContext())
                    .load(order.imgFood)
                    .into(binding.imgFood)

                binding.tvNameFood.text = order.nameFood
                binding.tvPriceFood.text = formatCurrency
            }
        })

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

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }


}