package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.DrinkFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentDrinkBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterBurger
import com.example.my_ecomerge_app.view.adapter.AdapterDrink
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinkFragment : Fragment() {

    private val binding by lazy { FragmentDrinkBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listDrink = MyApplication.appDatabase.orderDao().getAllDrink()
            Log.d("burger", "${listDrink}")
            val adapter = AdapterDrink(listDrink.toMutableList(), requireContext())
            binding.rcvDrink.adapter = adapter
            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listDrink[position]
                    val fragmentDetail = DetailFragment()
                    val order = Order(
                        selectedItem.imgFood,
                        selectedItem.nameFood,
                        selectedItem.price,
                        selectedItem.describe,
                        selectedItem.date,
                        selectedItem.type
                    )
                    val bundle = Bundle().apply {
                        putParcelable("order", order)
                    }
                    sharedViewModel.setSharedData(order)
                    fragmentDetail.arguments = bundle
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentDetail)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

            })

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


}