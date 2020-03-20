package com.example.simpleapp.model

sealed class Event

// Screen Load - Initial Event
object ScreenLoad : Event()

// User interaction
data class EndOfListReached(
    val listSize: Int
) : Event()
