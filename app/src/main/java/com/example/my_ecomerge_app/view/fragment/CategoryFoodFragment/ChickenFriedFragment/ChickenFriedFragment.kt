package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.ChickenFriedFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentChickenFriedBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterFriedChicken
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.AddFoodFragment.AddFoodFragment
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChickenFriedFragment : Fragment() {

    private val chickenBinding by lazy { FragmentChickenFriedBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listFriedChicken = MyApplication.appDatabase.orderDao().getAllFriedChickenType()
            Log.d("fried", "${listFriedChicken}")
            val adapter = AdapterFriedChicken(listFriedChicken.toMutableList(), requireContext())
            chickenBinding.rcvChicken.adapter = adapter

            adapter.setOnItemClick(object : OnItemClick {
                @SuppressLint("CommitTransaction")
                override fun onItemClick(position: Int) {
                    val selectedItem = listFriedChicken[position]
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
                    Toast.makeText(
                        requireContext(),
                        "Click on ${selectedItem.nameFood}",
                        Toast.LENGTH_SHORT
                    ).show()
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
    ): View {
        // Inflate the layout for this fragment
        return chickenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}