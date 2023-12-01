package com.example.my_ecomerge_app.model.item_api

data class Address(
    val city: String,
    val countryCode: String,
    val countryName: String,
    val county: String,
    val district: String,
    val houseNumber: String,
    val label: String,
    val postalCode: String,
    val state: String,
    val street: String
)