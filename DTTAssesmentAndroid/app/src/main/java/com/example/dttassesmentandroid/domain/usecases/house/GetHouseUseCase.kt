package com.example.dttassesmentandroid.domain.usecases.house

import com.example.dttassesmentandroid.data.repository.HouseRepository
import javax.inject.Inject

class GetHouseUseCase @Inject constructor(
    private val houseRepository: HouseRepository
) {
    operator fun invoke(houseID: Int) = houseRepository.getHouseCached(houseID)
}