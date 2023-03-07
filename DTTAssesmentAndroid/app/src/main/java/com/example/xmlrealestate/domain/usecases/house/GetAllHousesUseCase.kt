package com.example.xmlrealestate.domain.usecases.house

import com.example.xmlrealestate.data.repository.HouseRepository
import javax.inject.Inject

class GetAllHousesUseCase @Inject constructor(
    private val houseRepository: HouseRepository
) {
    operator fun invoke() = houseRepository.getAllHouses()
}