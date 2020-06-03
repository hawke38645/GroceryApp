package com.hawke38645.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.Volley.*
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterOrder
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.UpdateCartIcon
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.Order
import com.hawke38645.groceryapp.models.OrderList
import kotlinx.android.synthetic.main.activity_order_display.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*



class OrderDisplayActivity : AppCompatActivity(), UpdateCartIcon {

    var textViewCartCount: TextView? = null
    var databaseHelper = DatabaseHelper()
    var sessionManager = SessionManager()
    var mList: ArrayList<Order> = ArrayList()
    private lateinit var adapterOrder : AdapterOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_display)

        init()
    }

    private fun init() {
        toolbar("Order History")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recycler_view_order_display.layoutManager = LinearLayoutManager(this)
        adapterOrder = AdapterOrder(this)
        recycler_view_order_display.adapter = adapterOrder
        recycler_view_order_display.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        getOrders(sessionManager.getUserId()!!)
    }

    private fun getOrders(userId: String) {
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, Endpoints.getOrdersByUserId(userId),
            Response.Listener {
                var gson = GsonBuilder().create()
                var orderList = gson.fromJson(it.toString(), OrderList::class.java)
                mList = orderList.data
                //Toast.makeText(this, "" + mList.size, Toast.LENGTH_SHORT).show()
                adapterOrder.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        requestQueue.add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)

        var item = menu.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_badge
        updateCartCount()
        view.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
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

    override fun updateCartCount() {
        var itemCount = databaseHelper.getAllItemQuantity()
        if (itemCount == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = itemCount.toString()
        }
    }
}
