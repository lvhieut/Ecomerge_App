package com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.NoodleFragment

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
import com.example.my_ecomerge_app.databinding.FragmentNoodleBinding
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.adapter.AdapterNoodle
import com.example.my_ecomerge_app.view.fragment.DetailFragment.DetailFragment
import com.example.my_ecomerge_app.view.fragment.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoodleFragment : Fragment() {

    private val bindingNoodleFragment by lazy { FragmentNoodleBinding.inflate(layoutInflater) }
    private val sharedViewModel: ShareVm by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listNoodle = MyApplication.appDatabase.orderDao().getAllNoodle()
//            Log.d("no", "${listNoodle}")
            val adapter =  AdapterNoodle(listNoodle.toMutableList(), requireContext())
            bindingNoodleFragment.rcvChicken.adapter = adapter
            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listNoodle[position]
                    Toast.makeText(requireContext(),"Click on ${selectedItem.nameFood}",Toast.LENGTH_SHORT).show()
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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingNoodleFragment.root
    }


}