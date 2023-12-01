package com.example.my_ecomerge_app.model.item_api

data class Item(
    val access: List<Acces>,
    val address: Address,
    val distance: Int,
    val houseNumberType: String,
    val id: String,
    val mapView: MapView,
    val position: Position,
    val resultType: String,
    val title: String
)