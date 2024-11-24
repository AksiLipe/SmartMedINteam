package com.example.smartmed.fragments

import com.example.smartmed.enums.Severity

data class Allergy(
    val name: String,
    val severity: Severity?= null
)