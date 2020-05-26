package com.hawke38645.groceryapp.models

//data class Address(
//    val __v: Int,
//    val _id: String,
//    val city: String,
//    val houseNo: String,
//    val location: String,
//    val mobile: String,
//    val name: String,
//    val pincode: Int,
//    val streetName: String,
//    val type: String,
//    val userId: String
//)

data class Address(
    var name: String? = null,
    var houseNo: String? = null,
    var mobile: String? = null,
    var streetName: String? = null,
    var location: String? = null,
    var city: String? = null,
    var pincode: String? = null,
    var userId: String? = null,
    var type: String? = null
) {
    companion object {
        const val KEY_NAME = "name"
        const val KEY_MOBILE = "mobile"
        const val KEY_HOUSE_NO = "houseNo"
        const val KEY_STREET_NAME = "streetName"
        const val KEY_LOCATION = "location"
        const val KEY_CITY = "city"
        const val KEY_PIN_CODE = "pincode"
        const val KEY_USER_ID = "userId"
        const val KEY_TYPE = "type"
    }
}