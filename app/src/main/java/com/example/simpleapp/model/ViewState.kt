package com.example.simpleapp.model

data class ViewState(
    val catFacts: List<CatFact> = emptyList(),
    val errorMessage: String? = null,
    val loading: Boolean = false
)
