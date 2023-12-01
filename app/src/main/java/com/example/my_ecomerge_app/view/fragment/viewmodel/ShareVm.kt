package com.example.my_ecomerge_app.view.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_ecomerge_app.model.Cart
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.network.GetLocation
import com.example.my_ecomerge_app.view.fragment.HomeFragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareVm : ViewModel() {



    // LiveData để theo dõi thay đổi dữ liệu
    private val sharedData = MutableLiveData<Order>()
     val listSt = MutableLiveData<List<Cart>>()

    // Phương thức để cập nhật dữ liệu
    fun setSharedData(order: Order) {
        sharedData.value = order
    }

    // Phương thức để lấy dữ liệu
    fun getSharedData(): LiveData<Order> {
        return sharedData
    }











}