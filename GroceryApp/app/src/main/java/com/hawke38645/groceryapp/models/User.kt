package com.hawke38645.groceryapp.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firstName")
    var name: String? = null,
    var mobile: String? = null,
    var email: String? = null,
    var password: String? = null,
    var _id: String? = null
) {
    companion object {
        const val KEY_NAME = "name"
        const val KEY_USER_ID = "_id"
        const val KEY_MOBILE = "mobile"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
    }
}