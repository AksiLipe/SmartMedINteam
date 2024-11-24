package com.example.smartmed.repository

import com.example.smartmed.model.AppointmentModel

object AppointmentRepository {
    fun generateSampleAppointments(): List<AppointmentModel> {
        return listOf(
            AppointmentModel(
                imageUrl = "https://medcentre-mytishi.ru/uploads/010321/010221/23.png",
                fio = "Иванов Иван Иванович",
                post = "Врач-терапевт",
                date = "03.01.2024",
                dayOfWeek = "Понедельник",
                startTime = "09:00",
                endTime = "10:00"
            ),
            AppointmentModel(
                imageUrl = "https://www.nasdaq.com/sites/acquia.prod/files/image/5f292ec4247cf4e6a041356501b6cb6db9674192_doctor-giving-the-thumbs-up.jpg",
                fio = "Петров Петр Петрович",
                post = "Кардиолог",
                date = "04.02.2024",
                dayOfWeek = "Вторник",
                startTime = "10:00",
                endTime = "11:00"
            ),
            AppointmentModel(
                imageUrl = "https://avatars.mds.yandex.net/i?id=1cf7ebd4888ff6262e3fb7071bcc2efbdccdedd7-10677603-images-thumbs&ref=rim&n=33&w=374&h=250",
                fio = "Сидорова Анна Сергеевна",
                post = "Кардиолог",
                date = "05.03.2024",
                dayOfWeek = "Среда",
                startTime = "08:30",
                endTime = "9:30"
            ),
            AppointmentModel(
                imageUrl = "https://cdn.nur.kz/images/1120/d8632fd2ed72d72c.jpeg",
                fio = "Кузнецов Алексей Викторович",
                post = "Кардиолог",
                date = "13.01.2024",
                dayOfWeek = "Четверг",
                startTime = "11:00",
                endTime = "12:00"
            ),
            AppointmentModel(
                imageUrl = "https://avatars.mds.yandex.net/i?id=34455950c371e25934b508d9b49e99d7_l-5210051-images-thumbs&n=13",
                fio = "Смирнова Екатерина Андреевна",
                post = "Врач-терапевт",
                date = "31.01.2024",
                dayOfWeek = "Пятница",
                startTime = "16:30",
                endTime = "17:30"
            )
        )
    }
}