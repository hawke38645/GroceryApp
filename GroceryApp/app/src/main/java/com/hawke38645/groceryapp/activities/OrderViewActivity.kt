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
import com.google.gson.Gson
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.models.Order
import com.hawke38645.groceryapp.models.Order.Companion.KEY_ORDER
import com.hawke38645.groceryapp.models.Users
import kotlinx.android.synthetic.main.activity_order_view.*
import org.json.JSONObject

class OrderViewActivity : AppCompatActivity() {

    val sessionManager = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_view)

        val order = intent.getSerializableExtra(KEY_ORDER) as Order
        init(order)
    }

    private fun init(order: Order) {
        text_view_order_detail_name.text = "Name: \t\t\t\t\t\t\t\t\t\t${sessionManager.getName()}"
        text_view_order_detail_mobile.text = "Mobile: \t\t\t\t\t\t\t\t\t${sessionManager.getMobile()}"
        text_view_order_detail_email.text = "Email: \t\t\t\t\t\t\t\t\t\t${sessionManager.getEmail()}"
        text_view_order_detail_order_id.text = "Order ID: \t\t\t\t\t\t\t\t${order._id}"

        text_view_order_detail_street_name.text = "Street Name: \t\t\t\t\t\t${order.shippingAddress.streetName}"
        text_view_order_detail_city.text = "City: \t\t\t\t\t\t\t\t\t\t\t${order.shippingAddress.city}"
        text_view_order_detail_pincode.text = "Pin Code: \t\t\t\t\t\t\t\t${order.shippingAddress.pincode.toString()}"

        text_view_order_detail_total.text = "Total: \t\t\t\t\t\t\t\t\t\t₹${order.orderSummary?.totalAmount.toString()}"
        text_view_order_detail_discount.text = "Discount: \t\t\t\t\t\t\t\t₹${order.orderSummary?.discount.toString()}"

        btn_reorder.setOnClickListener {
            reorder(order)
            startActivity(Intent(this, ThankYouActivity::class.java))
        }
    }

    private fun reorder(order: Order) {
        var requestQueue = Volley.newRequestQueue(this)
        var jsonObject = JSONObject(Gson().toJson(order))

        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.postOrder(), jsonObject,
            Response.Listener { response ->
                Log.d("Response API: ", response.toString())
                Toast.makeText(this, "You have successfully posted an order!", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { response ->
                Log.d("ResponseAPI: ERROR: ", response.toString())
                //Toast.makeText(this, "Error posting...", Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(request)
    }
}
