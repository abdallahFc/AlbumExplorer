package com.example.albumexplorer.data.remote.mapper

import com.example.albumexplorer.data.remote.model.AddressDto
import com.example.albumexplorer.data.remote.model.UsersDto
import com.example.albumexplorer.domain.model.Address
import com.example.albumexplorer.domain.model.User

fun UsersDto.toUser()=User(
    username = username ?: "",
    address=address?.toAddress() ?:Address.createDefault()
)
fun AddressDto.toAddress()=Address(
    city=city ?: "",
    street=street ?:"",
    suite=suite ?: "",
    zipcode=zipcode ?: ""
)