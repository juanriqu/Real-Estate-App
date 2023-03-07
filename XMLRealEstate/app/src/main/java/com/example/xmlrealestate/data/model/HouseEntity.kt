package com.example.xmlrealestate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.domain.model.House
import com.google.gson.annotations.SerializedName

/**
 * [SerializedName] is used to tell Gson which is the name of the field in the JSON response.
 *
 * [ColumnInfo] is used to tell Room which is the name of the column in the database.
 */
@Entity(tableName = "house")
data class HouseEntity(
    @PrimaryKey(autoGenerate = false) @SerializedName(Constants.HOUSE_ID_COLUMN) @ColumnInfo(name = Constants.HOUSE_ID_COLUMN) val id: Int,
    @SerializedName(Constants.HOUSE_IMAGE_COLUMN) @ColumnInfo(name = Constants.HOUSE_IMAGE_COLUMN) val image: String,
    @SerializedName(Constants.HOUSE_PRICE_COLUMN) @ColumnInfo(name = Constants.HOUSE_PRICE_COLUMN) val price: Int,
    @SerializedName(Constants.HOUSE_BEDROOMS_COLUMN) @ColumnInfo(name = Constants.HOUSE_BEDROOMS_COLUMN) val bedrooms: Int,
    @SerializedName(Constants.HOUSE_BATHROOMS_COLUMN) @ColumnInfo(name = Constants.HOUSE_BATHROOMS_COLUMN) val bathrooms: Int,
    @SerializedName(Constants.HOUSE_SIZE_COLUMN) @ColumnInfo(name = Constants.HOUSE_SIZE_COLUMN) val size: Int,
    @SerializedName(Constants.HOUSE_DESCRIPTION_COLUMN) @ColumnInfo(name = Constants.HOUSE_DESCRIPTION_COLUMN) val description: String,
    @SerializedName(Constants.HOUSE_ZIP_COLUMN) @ColumnInfo(name = Constants.HOUSE_ZIP_COLUMN) val zipCode: String,
    @SerializedName(Constants.HOUSE_CITY_COLUMN) @ColumnInfo(name = Constants.HOUSE_CITY_COLUMN) val city: String,
    @SerializedName(Constants.HOUSE_LATITUDE_COLUMN) @ColumnInfo(name = Constants.HOUSE_LATITUDE_COLUMN) val latitude: Int,
    @SerializedName(Constants.HOUSE_LONGITUDE_COLUMN) @ColumnInfo(name = Constants.HOUSE_LONGITUDE_COLUMN) val longitude: Int,
    @SerializedName(Constants.HOUSE_CREATION_DATE_COLUMN) @ColumnInfo(name = Constants.HOUSE_CREATION_DATE_COLUMN) val creationDate: String
)

/**
 * Extension function to convert [HouseEntity] to [House].
 */
fun HouseEntity.toHouse(): House = House(
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
