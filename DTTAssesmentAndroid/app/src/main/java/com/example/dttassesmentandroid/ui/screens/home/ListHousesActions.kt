package com.example.dttassesmentandroid.ui.screens.home

import com.example.dttassesmentandroid.domain.model.House

interface ListHousesActions {
    fun onHouseClicked(house: House)
    fun giveMeDistance(house: House): Double
}