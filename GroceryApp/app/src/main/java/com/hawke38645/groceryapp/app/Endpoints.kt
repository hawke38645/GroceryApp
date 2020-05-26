package com.hawke38645.groceryapp.app

class Endpoints {

    companion object {

        //Setting up the pieces to make the correct calls for the API.
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB_ID = "products/sub/"
        private const val URL_AUTHENTICATION = "auth/"
        private const val URL_ADDRESS = "address/"
        private const val URL_LOGIN = "login"
        private const val URL_REGISTER = "register"
        private const val URL_ORDERS = "orders/"

        fun getCategory(): String {
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId}"
        }

        fun getProductsBySubId(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB_ID + subId}"
        }

        fun login(): String{
            return "${Config.BASE_URL + URL_AUTHENTICATION+ URL_LOGIN}"
        }

        fun register(): String{
            return "${Config.BASE_URL + URL_AUTHENTICATION+ URL_REGISTER}"
        }

        fun getAddressesByUserId(userId: String): String {
            return "${Config.BASE_URL + URL_ADDRESS + userId}"
        }

        fun postAddress(): String {
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun getOrdersByUserId(userId: String): String {
           return "${Config.BASE_URL + URL_ORDERS + userId}"
        }

        fun postOrder(): String {
            return "${Config.BASE_URL + URL_ORDERS}"
        }

    }

}