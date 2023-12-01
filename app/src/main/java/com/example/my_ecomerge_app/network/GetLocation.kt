package com.example.my_ecomerge_app.network

import com.example.my_ecomerge_app.model.item_api.item_api
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetLocation {
    @GET("/revgeocode")
    suspend fun  getApiData(
        @Query("lat") lat :Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "vi-VN",
        @Query("apiKey") apiKey: String = "Qb3Tu95Z0Lbgc1PrbF53OlIIZ1FlSNfKRB1N3RScV4Y"
    ): Response<item_api>
}