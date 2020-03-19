package com.example.simpleapp.model

import java.util.*

data class CatFact(
    val user: String,
    val text: String,
    val updatedAt: Date,
    val createdAt: Date,
    val deleted: Boolean
)