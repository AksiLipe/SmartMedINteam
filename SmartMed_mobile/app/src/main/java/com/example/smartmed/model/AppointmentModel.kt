package com.example.smartmed.model

data class AppointmentModel(
    val imageUrl: String,
    val fio: String,
    val post: String,
    val date: String,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String
)
