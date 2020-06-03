package com.hawke38645.groceryapp.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Order(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderStatus: String? = null,
    val orderSummary: Summary? = null,
    val payment: Payment? = null,
    val products: ArrayList<Product>? = null,
    val shippingAddress: ShippingAddress,
    @SerializedName("user")
    var users: Users,
    val userId: String
): Serializable {
    companion object {
        const val KEY_ORDER = "order"
    }
}

data class Summary(
    var _id: String? = null,
    var deliveryCharges: Int,
    var discount: Double,
    var orderAmount: Int? = null,
    var ourPrice: Double? = null,
    var totalAmount:Int? = null
): Serializable

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
): Serializable

data class Product(
    var _id: String,
    var image: String,
    var mrp: Double,
    var price: Double,
    var quantity: Int
): Serializable

data class ShippingAddress(
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String
): Serializable

data class Users(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
): Serializable