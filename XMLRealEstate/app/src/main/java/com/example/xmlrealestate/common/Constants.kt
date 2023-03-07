package com.example.xmlrealestate.common

object Constants {

    //API Constants
    const val BASE_URL = "https://intern.d-tt.nl/api/"
    const val API_KEY = "98bww4ezuzfePCYFxJEWyszbUXc7dxRx"
    const val IMAGE_RELATIVE_PATH = "https://intern.d-tt.nl/"

    //House Constants
    const val HOUSE_ID_COLUMN = "id"
    const val HOUSE_IMAGE_COLUMN = "image"
    const val HOUSE_PRICE_COLUMN = "price"
    const val HOUSE_BEDROOMS_COLUMN = "bedrooms"
    const val HOUSE_BATHROOMS_COLUMN = "bathrooms"
    const val HOUSE_SIZE_COLUMN = "size"
    const val HOUSE_DESCRIPTION_COLUMN = "description"
    const val HOUSE_ZIP_COLUMN = "zip"
    const val HOUSE_CITY_COLUMN = "city"
    const val HOUSE_LATITUDE_COLUMN = "latitude"
    const val HOUSE_LONGITUDE_COLUMN = "longitude"
    const val HOUSE_CREATION_DATE_COLUMN = "createdDate"
    const val NO_DISTANCE_AVAILABLE = "N/A"

    //DatabaseModule Constants
    const val DB_PATH = "database/houses.db"
    const val ASSET_DB = "assetDB"
    const val HOUSES_DATABASE = "houses_database"

    //AboutFragment Constants
    const val GITHUB_PROFILE_URL = "https://github.com/juanriqu"

    //AuthInterceptor Constants
    const val API_KEY_HEADER = "Access-Key"

    //Navigation Constants
    const val HOUSE_ID_ARG = "houseId"

    //Fragments Titles
    const val LIST_HOUSES_FRAGMENT_TITLE = "DTT REAL ESTATE"
    const val ABOUT_FRAGMENT_TITLE = "ABOUT"

    //House Details Constants
    const val DOLLAR_CHARACTER = "$"
    const val KM = "km"
    const val NO_DISTANCE_LOCATION_DISABLED = "N/A"

    //Query Constants
    const val QUERY_GET_ALL_HOUSES_PRICE_ASC = "SELECT * FROM house order by price ASC"
    const val QUERY_GET_HOUSE_BY_ID = "SELECT * FROM house WHERE id = :id"

    //Errors Constants
    const val ERROR_TOAST_INDICATOR = "Error: "
    const val NO_INTERNET_CONNECTION_ERROR = "No internet connection"
}