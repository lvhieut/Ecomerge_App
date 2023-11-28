package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.CoffeeFragment

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
import com.example.my_ecomerge_app.databinding.FragmentCoffeeBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterCoffee
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeeFragment : Fragment() {

    private val coffeeBinding by lazy { FragmentCoffeeBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listCoffee = MyApplication.appDatabase.orderDao().getAllCoffee()
            Log.d("fried", "${listCoffee}")
            val adapter = AdapterCoffee(listCoffee.toMutableList(), requireContext())
            coffeeBinding.rcvCoffee.adapter = adapter

            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listCoffee[position]
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
    ): View? {
        // Inflate the layout for this fragment
        return coffeeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}