package com.example.xmlrealestate.ui.screens.home

import com.example.xmlrealestate.domain.model.House

interface ListHouseContract {
    //We use this "Contract" class to define the events and state, instead of having a class for each one.
    sealed class Event {
        object OnSearchClosed : Event()
        object GetHouses : Event()
        object OnErrorShown : Event()
    }

    data class State(
        val houses: List<House>?,
        val isLoading: Boolean = false,
        val error: String?
    )
}