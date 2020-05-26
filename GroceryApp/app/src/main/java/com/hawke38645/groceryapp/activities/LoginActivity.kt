package com.hawke38645.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.hide
import com.hawke38645.groceryapp.helpers.show
import com.hawke38645.groceryapp.models.AuthResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    var sessionManager = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        progress_bar_lgn.hide()
        btn_login.setOnClickListener {
            if (validateInput()) {
                progress_bar_lgn.show()

                val inputEmail = edit_text_email_lgn.text.toString()
                val inputPassword = edit_text_password_lgn.text.toString()
                loginUser(inputEmail, inputPassword)
                finish()
            }
        }
        text_view_clickable_lgn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(inputEmail: String, inputPassword: String) {
        var params = HashMap<String, String?>()
        params["email"] = inputEmail
        params["password"] = inputPassword

        var jsonObject = JSONObject(params as Map<*, *>)

        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.login(),
            jsonObject,
            Response.Listener {
                progress_bar_lgn.hide()
                Log.d("APIResponse: ", it.toString())
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                var gson = GsonBuilder().create()
                var authResponse = gson.fromJson(it.toString(), AuthResponse::class.java)
                sessionManager.register(authResponse.user)
                Toast.makeText(this, "You have successfully logged in!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CategoryActivity::class.java))
                finish()
            },
            Response.ErrorListener {
                progress_bar_lgn.hide()
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        Volley.newRequestQueue(this).add(request)
    }

    private fun validateInput(): Boolean {
        return when {
            edit_text_email_lgn.text.isNullOrEmpty() -> {
                input_layout_email_lgn.error = "Email is required."
                false
            }
            edit_text_password_lgn.text.isNullOrEmpty() -> {
                input_layout_password_lgn.error = "Password is required."
                false
            }
            else -> {
                true
            }
        }
    }
}