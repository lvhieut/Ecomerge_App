package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.TeaFragment

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
import com.example.my_ecomerge_app.databinding.FragmentTeaBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterSalad
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.viewmodel.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TeaFragment : Fragment() {

    private val binding by lazy { FragmentTeaBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            val listBubleTea = MyApplication.appDatabase.orderDao().getAllTea()
            Log.d("tea", "${listBubleTea}")
            val adapter =  AdapterSalad(listBubleTea.toMutableList(), requireContext())
            binding.rcvBubbleTea.adapter = adapter

            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listBubleTea[position]
                    Toast.makeText(requireContext(),"!!", Toast.LENGTH_SHORT).show()
                    val fragmentDetail = DetailFragment()
                    val order = Order(selectedItem.imgFood,
                        selectedItem.nameFood,
                        selectedItem.price,
                        selectedItem.describe,
                        selectedItem.date,
                        selectedItem.type)
                    val bundle = Bundle().apply {
                        putParcelable("order",order)
                    }
                    sharedViewModel.setSharedData(order)
                    fragmentDetail.arguments = bundle
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.framelayout,fragmentDetail)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            })
        }

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
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