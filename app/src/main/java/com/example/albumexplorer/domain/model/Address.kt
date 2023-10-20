package com.example.albumexplorer.domain.model
data class Address(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String
){
    companion object {
        fun createDefault() = Address("", "", "","")
    }
}
