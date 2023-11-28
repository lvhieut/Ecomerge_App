package com.example.my_ecomerge_app.view.fragment.GioHangFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentOrderBinding
import com.example.my_ecomerge_app.view.adapter.AdapterCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : Fragment() {




    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    private val binding by lazy { FragmentOrderBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val listCart = MyApplication.appDatabase.cartDao().getAllCart()
            Log.d("no", "${listCart}")
            val adapter = AdapterCart(listCart.toMutableList(), requireContext())

            if (listCart.isEmpty()){
                binding.tvNullCart.visibility = View.VISIBLE
            } else {
                binding.tvNullCart.visibility  = View.GONE
                binding.scrollViewCart.visibility = View.VISIBLE
            }

            with(binding){
                rcvGiohang.adapter = adapter
                rcvGiohang.setHasFixedSize(true)
                rcvGiohang.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
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