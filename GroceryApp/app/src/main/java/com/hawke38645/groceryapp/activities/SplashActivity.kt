package com.hawke38645.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.helpers.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, 3000)
    }

    private fun checkLogin() {
        val sessionManager = SessionManager()
        var intent = if(sessionManager.isLoggedIn()) {
            Intent(this, CategoryActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
