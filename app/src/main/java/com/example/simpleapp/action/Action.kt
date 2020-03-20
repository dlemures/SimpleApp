package com.example.simpleapp.action

import com.example.simpleapp.model.ViewState

interface Action {
    fun perform(currentState: ViewState): ViewState
}
