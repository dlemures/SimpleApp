package com.example.simpleapp.action

import com.example.simpleapp.model.ViewState

class StartLoading : Action {
    override fun perform(currentState: ViewState): ViewState = currentState.copy(
        loading = true,
        catFacts = emptyList(),
        errorMessage = null
    )
}
