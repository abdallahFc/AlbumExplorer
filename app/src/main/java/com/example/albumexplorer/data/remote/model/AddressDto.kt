package com.example.albumexplorer.data.remote.model

data class AddressDto(
    val city: String?,
    val geo: Geo?,
    val street: String?,
    val suite: String?,
    val zipcode: String?
)