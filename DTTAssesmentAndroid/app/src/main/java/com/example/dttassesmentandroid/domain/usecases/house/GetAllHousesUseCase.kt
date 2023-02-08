package com.example.dttassesmentandroid.domain.usecases.house

import com.example.dttassesmentandroid.data.repository.HouseRepository
import javax.inject.Inject

class GetAllHousesUseCase @Inject constructor(
    private val houseRepository: HouseRepository
) {
    operator fun invoke() = houseRepository.getAllHouses()
}