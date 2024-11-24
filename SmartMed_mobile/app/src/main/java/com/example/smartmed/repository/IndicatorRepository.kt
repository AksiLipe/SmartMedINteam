package com.example.smartmed.repository

import com.example.smartmed.model.IndicatorModel
import kotlin.random.Random

object IndicatorRepository {
}

public fun generateIndicatorModel() : IndicatorModel {
    val pulse = Random.nextInt(60, 100).toString() // Генерация пульса от 60 до 99
    val pressure =
        "${Random.nextInt(110, 130)}/${Random.nextInt(70, 90)}" // Генерация АД (например, 120/80)
    val saturation = "${Random.nextInt(95, 100)}%" // Генерация сатурации от 95% до 99%
    val breathing = Random.nextInt(12, 20).toString() // Генерация частоты дыхания от 12 до 19

    return IndicatorModel(pulse, pressure, saturation, breathing)
}