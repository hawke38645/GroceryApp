package com.hawke38645.groceryapp.models

data class Category(
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
) {
    companion object{
        const val KEY_CAT_ID = "catId"
    }
}