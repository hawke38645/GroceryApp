package com.hawke38645.groceryapp.models

import java.io.Serializable

data class Products(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Double,
    val position: Int,
    val price: Double,          //Shouldn't price be a double as well?
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
): Serializable
{
    companion object {
        const val KEY_PRODUCT = "product"
    }
}