package com.example.my_ecomerge_app.view.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.my_ecomerge_app.model.item_api.item_api
import com.example.my_ecomerge_app.network.GetLocation

class Repository(private val api: GetLocation) {

    private val apiLiveData = MutableLiveData<item_api>()
    val apiGet : LiveData<item_api> = apiLiveData
    suspend fun getApiData(lat : Double, lon : Double) {
        val result = api.getApiData(lat, lon)
        if (result.body() != null){
            apiLiveData.postValue(result.body())
        }
    }
}