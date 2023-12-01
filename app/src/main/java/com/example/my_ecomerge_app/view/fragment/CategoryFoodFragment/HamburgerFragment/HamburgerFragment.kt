package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.HamburgerFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentHamburgerBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterBurger
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.viewmodel.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HamburgerFragment : Fragment() {

    private val burgerScreenBinding by lazy { FragmentHamburgerBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()

    companion object {
        fun BurgerInstance(): HamburgerFragment {
            return HamburgerFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listBurger = MyApplication.appDatabase.orderDao().getAllBurgersType()
            Log.d("burger", "${listBurger}")
            val adapter = AdapterBurger(listBurger.toMutableList(), requireContext())
            burgerScreenBinding.rcvHamburger.adapter = adapter

            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listBurger[position]

                    Toast.makeText(requireContext(), "Click on $", Toast.LENGTH_SHORT).show()
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
        return burgerScreenBinding.root
    }

}