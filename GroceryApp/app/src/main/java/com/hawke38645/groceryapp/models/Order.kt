package com.hawke38645.groceryapp.models

data class Order(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderStatus: String? = null,
    val orderSummary: Summary? = null,
    val payment: Payment? = null,
    val products: ArrayList<Product>? = null,
    val shippingAddress: ShippingAddress,
    val users: Users,
    val userId: String
)

data class Summary(
    var _id: String? = null,
    var deliveryCharges: Int,
    var discount: Double,
    var orderAmount: Int? = null,
    var ourPrice: Double? = null,
    var totalAmount:Int? = null
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class Product(
    var _id: String,
    var image: String,
    var mrp: Double,
    var price: Double,
    var quantity: Int
)

data class ShippingAddress(
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String
)

data class Users(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
)

//import com.google.gson.annotations.SerializedName
//
//data class Order(
//    val __v: Int? = null,
//    val _id: String? = null,
//    val date: String? = null,
//    val orderStatus: String? = null,
//    val orderSummary: OrderSummary? = null,
//    val payment: Payment? = null,
//    @SerializedName("products")
//    val orderProducts: List<OrderProduct>? = null,
//    val shippingAddress: ShippingAddress? = null,
//    @SerializedName("user")
//    val orderUser: OrderUser? = null,
//    val userId: String? = null
//)
//
//data class OrderSummary(
//    val _id: String? = null,
//    val deliveryCharges: Double? = null,
//    val discount: Double? = null,
//    val orderAmount: Double? = null,
//    val ourPrice: Double? = null,
//    val totalAmount: Double? = null
//)
//
//data class Payment(
//    val _id: String? = null,
//    val paymentMode: String? = null,
//    val paymentStatus: String? = null
//)
//
//data class OrderProduct(
//    var _id: String? = null,
//    var image: String? = null,
//    var mrp: Double? = null,
//    var price: Double? = null,
//    var quantity: Int? = null
//)
//
//data class ShippingAddress(
//    val city: String? = null,
//    val houseNo: String? = null,
//    val pincode: Int? = null,
//    val streetName: String? = null
//)
//
//data class OrderUser(
//    val _id: String? = null,
//    val email: String? = null,
//    val mobile: String? = null,
//    val name: String? = null
//)

//data class Order(
//    val __v: Int,
//    val _id: String,
//    val date: String,
//    val orderStatus: String,
//    val orderSummary: OrderSummary,
//    val payment: Payment,
//    val products: List<Product>,
//    val shippingAddress: ShippingAddress,
//    val user: User,
//    val userId: String
//)
//
//data class OrderSummary(
//    val _id: String,
//    val deliveryCharges: Int,
//    val discount: Int,
//    val orderAmount: Int,
//    val ourPrice: Int,
//    val totalAmount: Int
//)
//
//data class Payment(
//    val _id: String,
//    val paymentMode: String,
//    val paymentStatus: String
//)
//
//data class Product(
//    val _id: String,
//    val image: String,
//    val mrp: Double,
//    val price: Double,
//    val quantity: Int
//)
//
//data class ShippingAddress(
//    val city: String,
//    val houseNo: String,
//    val pincode: Int,
//    val streetName: String
//)
//
//data class User(
//    val _id: String,
//    val email: String,
//    val mobile: String,
//    val name: String
//)