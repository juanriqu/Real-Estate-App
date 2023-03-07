package com.example.xmlrealestate.domain.model

import com.example.xmlrealestate.data.model.HouseEntity

data class House(
    val id: Int,
    val image: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val size: Int,
    val description: String,
    val zipCode: String,
    val city: String,
    val latitude: Int,
    val longitude: Int,
    val creationDate: String,
    var distance: String? = null,
)

/**
 * Extension function to convert [House] to [HouseEntity].
 */
fun House.toHouseEntity(): HouseEntity = HouseEntity(
    id = this.id,
    image = this.image,
    price = this.price,
    bedrooms = this.bedrooms,
    bathrooms = this.bathrooms,
    size = this.size,
    description = this.description,
    zipCode = this.zipCode,
    city = this.city,
    latitude = this.latitude,
    longitude = this.longitude,
    creationDate = this.creationDate,
)