package com.example.xmlrealestate.ui.screens.home

import com.example.xmlrealestate.domain.model.House

interface ListHousesActions {
    fun onHouseClicked(house: House)
}