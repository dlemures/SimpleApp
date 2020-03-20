package com.example.simpleapp.action

import com.example.simpleapp.model.CatFact
import com.example.simpleapp.model.ViewState

// This screen is very simple so we just process and set the values inside the ViewState one by one.
// On a more complex screen, the ViewState would be composed of small UI models (representing one section of the screen each)
// So each of those UI models would be generated from the Network Data (a other sources like the previous state) by using
// their own converters:
//
//  Network Data                   /-- converter A ->   UI Model A  \
//  Previous ViewState  => Action  --- converter B ->   UI Model B  --> New ViewState
//  Other data                     \-- converter C ->   UI Model C  /
//
class AddCatFacts(private val catFacts: List<CatFact>) : Action {
    override fun perform(currentState: ViewState): ViewState = currentState.copy(
        catFacts = currentState.catFacts + catFacts,
        loading = false,
        errorMessage = null
    )
}
