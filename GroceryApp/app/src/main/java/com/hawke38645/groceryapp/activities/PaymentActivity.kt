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
import com.android.volley.toolbox.Volley.*
import com.google.gson.Gson
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.*
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    var dataBaseHelper = DatabaseHelper()
    var sessionManager = SessionManager()
    var products: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        init()
    }

    private fun init() {
        toolbar("Payment")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_goto_order.setOnClickListener {
            postOrder()
            startActivity(Intent(this, ThankYouActivity::class.java))
        }

    }

    private fun postOrder() {
        var orderUserId = sessionManager.getUserId()!!
        var orderUserEmail = sessionManager.getEmail()!!
        var orderUserMobile = sessionManager.getMobile()!!
        var orderUserName = sessionManager.getName()!!

        var users = Users(orderUserId, orderUserEmail, orderUserMobile, orderUserName)

        var shpAdrCity = sessionManager.getPrimaryAddress().city!!
        var shpAdrHouseNo = sessionManager.getPrimaryAddress().houseNo!!
        var shpAdrPincode = sessionManager.getPrimaryAddress().pincode?.toInt()!!
        var shpAdrStreetName = sessionManager.getPrimaryAddress().streetName!!

        var shippingAddress = ShippingAddress(shpAdrCity, shpAdrHouseNo, shpAdrPincode, shpAdrStreetName)

        var orderSum = dataBaseHelper.calculateOrderSummary()
        var summary = Summary(deliveryCharges = 2, discount = orderSum.discount.toDouble(), totalAmount = orderSum.totalPrice.toInt())

        var mList: ArrayList<CartItem> = dataBaseHelper.readCartItems()

        for (i in 0 until mList.size) {
            var product = Product(mList[i]._id, mList[i].imageURL, mList[i].mrp, mList[i].price, mList[i].quantity)
            products.add(product)
        }

        //Order(_v, _id, date, orderStatus, orderSummary, payment, orderProducts, shippingAddress, orderUser, userId)
        var order = Order(shippingAddress = shippingAddress, users = users, orderSummary = summary, userId = sessionManager.getUserId()!!, products = products)

        var requestQueue = newRequestQueue(this)
        var jsonObject = JSONObject(Gson().toJson(order))

        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.postOrder(), jsonObject,
            Response.Listener { response ->
                Log.d("Response API: ", response.toString())
                Toast.makeText(this, "You have successfully posted an order!", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {response ->
                Log.d("ResponseAPI: ERROR: ", response.toString())
                Toast.makeText(this, "Error posting...", Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(request)
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
