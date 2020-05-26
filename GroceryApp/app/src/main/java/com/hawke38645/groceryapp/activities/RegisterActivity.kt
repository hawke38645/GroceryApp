package com.hawke38645.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.hide
import com.hawke38645.groceryapp.helpers.show
import com.hawke38645.groceryapp.models.User
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        progress_bar_reg.hide()
        btn_register.setOnClickListener {
            if (validateInput()) {
                progress_bar_reg.show()
                val sessionManager = SessionManager()
                val inputName = edit_text_name_reg.text.toString()
                val inputMobile = edit_text_mobile_reg.text.toString()
                val inputEmail = edit_text_email_reg.text.toString()
                val inputPassword = edit_text_password_reg.text.toString()

                //sessionManager.register(User(inputName, inputMobile, inputEmail, inputPassword))
                registerUser(User(inputName, inputMobile, inputEmail, inputPassword))
            }
        }
        text_view_clickable_reg.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateInput(): Boolean {
        when {
            edit_text_name_reg.text.isNullOrEmpty() -> {
                input_layout_name_reg.error = "Name is required."
                return false
            }
            edit_text_mobile_reg.text.isNullOrEmpty() -> {
                input_layout_mobile_reg.error = "Mobile is required."
                return false
            }
            edit_text_email_reg.text.isNullOrEmpty() -> {
                input_layout_email_reg.error = "Email is required."
                return false
            }
            edit_text_password_reg.text.isNullOrEmpty() -> {
                input_layout_password_reg.error = "Password is required."
                return false
            }
            else -> {
                return true
            }
        }
    }


    private fun registerUser(user: User) {
        var params = HashMap<String, String?>()
        params["firstName"] = user.name
        params["mobile"] = user.mobile
        params["email"] = user.email
        params["password"] = user.password

        var jsonObject = JSONObject(params as Map<*, *>)

        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.register(),
            jsonObject,
            Response.Listener {
                progress_bar_reg.hide()
                Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show()
                var sessionManager = SessionManager()
                sessionManager.register(user)
                startActivity(Intent(this, LoginActivity::class.java))
            },
            Response.ErrorListener {
                progress_bar_reg.hide()
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        Volley.newRequestQueue(this).add(request)
    }
}
