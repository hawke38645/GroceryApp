package com.hawke38645.groceryapp.models

data class AuthResponse (
    var token: String,
    var user: User
)