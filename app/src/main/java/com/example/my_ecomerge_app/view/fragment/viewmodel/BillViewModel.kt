package com.example.my_ecomerge_app.view.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.my_ecomerge_app.model.item_api.item_api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillViewModel(private val repo : Repository) : ViewModel() {
    fun fetchApiData(lat: Double, lon: Double){
        CoroutineScope(Dispatchers.IO).launch {
            repo.getApiData(lat, lon)
        }
    }
    val apiLocation : LiveData<item_api> = repo.apiGet
}