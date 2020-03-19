package com.example.simpleapp.model

import java.lang.Exception

sealed class State

data class Data(
    val catFacts: List<CatFact>
) : State()

data class Error(
    val important: Boolean,
    val message: String,
    val throwable: Throwable
) : State()

object Loading : State()
