package com.example.smartmed.fragments

import com.example.smartmed.enums.Severity

data class Disease(
    val name: String,
    val severity: Severity ?= null
)
