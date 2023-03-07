package com.example.xmlrealestate.ui.screens.detailedHouse

import com.example.xmlrealestate.domain.model.House

interface DetailedHouseContract {
    sealed class Event {
        class GetHouse(val houseId: Int) : Event()
        object OnErrorShown : Event()
    }

    data class State(
        val house : House,
        val isLoading: Boolean = false,
        val error: String?
    )
}