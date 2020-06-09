package com.example.timebook.model

data class Driver(
    val name: String,
    val driverCode: String,
    val address: String,
    val phoneNumber: String,
    val employee: MutableList<Employee>
)