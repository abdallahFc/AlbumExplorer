package com.example.albumexplorer.data.remote.model

data class UsersDto(
    val address: AddressDto?,
    val company: CompanyDto?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
)