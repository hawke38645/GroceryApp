package com.hawke38645.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.Address
import kotlinx.android.synthetic.main.activity_add_address.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {

    val sessionManager = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun init() {
        progress_bar_add_address.hide()
        toolbar("Create a New Address")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_add_address.setOnClickListener {
            progress_bar_add_address.show()
            addAddress()
        }
    }

    private fun addAddress() {
        var city = edit_text_city_address.text.toString()
        var location = edit_text_location_address.text.toString()
        var houseNo = edit_text_house_number_address.text.toString()
        var pincode = edit_text_pin_code.text.toString()
        var streetName = edit_text_street_address.text.toString()
        var type = edit_text_building_type.text.toString()
        var mobile = sessionManager.getMobile()
        var name = sessionManager.getName()
        var userId = sessionManager.getUserId()

        var address = Address(name, houseNo, mobile, streetName, location, city,  pincode, userId, type)

        var params = HashMap<String, String?>()

        params["name"] = address.name
        params["houseNo"] = address.houseNo
        params["mobile"] = address.mobile
        params["streetName"] = address.streetName
        params["location"] = address.location
        params["city"] = address.city
        params["pincode"] = address.pincode
        params["userId"] = address.userId
        params["type"] = address.type

        var jsonObject = JSONObject(params as Map<*, *>)

        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.postAddress(),
            jsonObject,
            Response.Listener {
                progress_bar_add_address.hide()
                Log.d("APIResponse: ", it.toString())
                Toast.makeText(this, "You have successfully posted an address!", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                progress_bar_add_address.hide()
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        Volley.newRequestQueue(this).add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate((R.menu.cart_checkout_menu), menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //ID of the back-button; it simply finishes the activity and returns home
            android.R.id.home -> {
                finish()
            }
            R.id.menu_profile -> {
                Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_settings -> {
                Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}
