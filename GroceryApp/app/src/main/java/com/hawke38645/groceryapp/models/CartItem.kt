package com.hawke38645.groceryapp.models

import java.io.Serializable

data class CartItem(
    val _id: String,
    val productName: String,
    val price: Double,
    val mrp: Double,
    val imageURL: String,
    var quantity: Int

): Serializable
{
    companion object {
        const val KEY_CART_ITEM = "cartItem"
    }
}