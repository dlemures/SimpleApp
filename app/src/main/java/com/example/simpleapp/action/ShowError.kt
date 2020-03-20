package com.example.simpleapp.action

import com.example.simpleapp.model.ViewState

class ShowError(private val errorMessage: String) : Action {
    override fun perform(currentState: ViewState): ViewState = currentState.copy(
        errorMessage = errorMessage,
        catFacts = emptyList(),
        loading = false
    )
}
