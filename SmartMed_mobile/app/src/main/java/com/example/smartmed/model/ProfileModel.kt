package com.example.smartmed.model

import com.example.smartmed.enums.Gender
import com.example.smartmed.fragments.Allergy
import com.example.smartmed.fragments.Disease

data class ProfileModel(
    val imageUrl: String,
    val login: String,
    val age: String,
    val gender: Gender,
    val pregnant: Boolean,
    val diseases: List<Disease>,
    val allergies: List<Allergy>
)
