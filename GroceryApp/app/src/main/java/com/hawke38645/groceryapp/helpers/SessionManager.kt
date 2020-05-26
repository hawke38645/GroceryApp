package com.hawke38645.groceryapp.helpers

import android.content.Context
import com.hawke38645.groceryapp.app.Config
import com.hawke38645.groceryapp.app.MyApplication
import com.hawke38645.groceryapp.models.Address
import com.hawke38645.groceryapp.models.User

class SessionManager {

    private var sharedPreferences = MyApplication.instance.getSharedPreferences(Config.FILE_NAME, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun register(user: User) {
        editor.putString(User.KEY_NAME, user.name)
        editor.putString(User.KEY_MOBILE, user.mobile)
        editor.putString(User.KEY_EMAIL, user.email)
        editor.putString(User.KEY_PASSWORD, user.password)
        editor.putString(User.KEY_USER_ID, user._id)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }

    fun setPrimaryAddress(address: Address) {
        editor.putString(Address.KEY_NAME, address.name)
        editor.putString(Address.KEY_MOBILE, address.mobile)
        editor.putString(Address.KEY_HOUSE_NO, address.houseNo)
        editor.putString(Address.KEY_STREET_NAME, address.streetName)
        editor.putString(Address.KEY_LOCATION, address.location)
        editor.putString(Address.KEY_CITY, address.city)
        editor.putString(Address.KEY_PIN_CODE, address.pincode)
        editor.putString(Address.KEY_USER_ID, address.userId)
        editor.putString(Address.KEY_TYPE, address.type)
        editor.commit()
    }

    fun login(inputEmail: String, inputPassword: String): Boolean {
        var savedEmail = sharedPreferences.getString(User.KEY_EMAIL, null)
        var savedPassword = sharedPreferences.getString(User.KEY_PASSWORD, null)
        return inputEmail == savedEmail && inputPassword == savedPassword
    }

    fun getName(): String? {
        return sharedPreferences.getString(User.KEY_NAME, null)
    }

    fun getMobile(): String? {
        return sharedPreferences.getString(User.KEY_MOBILE, null)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(User.KEY_EMAIL, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(User.KEY_PASSWORD, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(User.KEY_USER_ID, null)
    }


    fun getUser(): User {
        val name = sharedPreferences.getString(User.KEY_NAME, null)
        val mobile = sharedPreferences.getString(User.KEY_MOBILE, null)
        val email = sharedPreferences.getString(User.KEY_EMAIL, null)
        val password = sharedPreferences.getString(User.KEY_PASSWORD, null)
        val _id = sharedPreferences.getString(User.KEY_USER_ID, null)
        return User(name, mobile, email, password, _id)
    }

    fun getPrimaryAddress(): Address {
        val name = sharedPreferences.getString(Address.KEY_NAME,null)
        val mobile = sharedPreferences.getString(Address.KEY_MOBILE, null)
        val houseNo = sharedPreferences.getString(Address.KEY_HOUSE_NO, null)
        val streetName = sharedPreferences.getString(Address.KEY_STREET_NAME, null)
        val location = sharedPreferences.getString(Address.KEY_LOCATION, null)
        val city = sharedPreferences.getString(Address.KEY_CITY,null)
        val pincode = sharedPreferences.getString(Address.KEY_PIN_CODE, null)
        val userId = sharedPreferences.getString(Address.KEY_USER_ID, null)
        val type = sharedPreferences.getString(Address.KEY_TYPE,null)
        return Address(name, houseNo, mobile, streetName, location, city,  pincode, userId, type)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

}