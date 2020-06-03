package com.hawke38645.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterCart
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.CartItem
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), AdapterCart.OnAdapterCartInteraction {

    private lateinit var adapterCart: AdapterCart
    private var databaseHelper = DatabaseHelper()
    val sessionManager = SessionManager()
    private var mList: ArrayList<CartItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
    }

    private fun init() {
        toolbar("Cart")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recycler_view_cart_activity.layoutManager = LinearLayoutManager(this)
        adapterCart = AdapterCart(this)
        adapterCart.setOnAdapterCartInteractionListener(this)
        recycler_view_cart_activity.adapter = adapterCart
        recycler_view_cart_activity.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )
        mList = databaseHelper.readCartItems()
        adapterCart.setData(mList)

        displayOrder()
        checkCartStatus()

        btn_cart_checkout.setOnClickListener {
            startActivity(Intent(this, AddressDisplayActivity::class.java))
        }
    }

    override fun onItemClicked(view: View, position: Int) {

        when(view.id) {
            R.id.btn_delete_cart_item -> {
            databaseHelper.deleteCartItem(mList[position])
            adapterCart.setData(databaseHelper.readCartItems())
            displayOrder()
            checkCartStatus()
            }
            R.id.btn_plus_cart_item -> {
            databaseHelper.updateCartItem(mList[position], true)
            adapterCart.setData(databaseHelper.readCartItems())
            displayOrder()
            checkCartStatus()
            }
            R.id.btn_minus_cart_item -> {
            databaseHelper.updateCartItem(mList[position], false)
            adapterCart.setData(databaseHelper.readCartItems())
            displayOrder()
            checkCartStatus()
            }
        }
    }

    private fun displayOrder() {
        var orderSummary = databaseHelper.calculateOrderSummary()
        text_view_cart_total_mrp.text = "MRP: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t₹${orderSummary.totalMrp}"
        text_view_cart_total_discount.text = "Discount: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t₹${orderSummary.discount}"
        text_view_cart_total_price.text = "Total: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t₹${orderSummary.totalPrice}"

    }

    private fun checkCartStatus() {
        mList = databaseHelper.readCartItems()

        if (mList.isEmpty()) {
            text_view_cart_empty.visibility = VISIBLE
            text_view_cart_total_mrp.visibility = INVISIBLE
            text_view_cart_total_price.visibility = INVISIBLE
            text_view_cart_total_discount.visibility = INVISIBLE
            btn_cart_checkout.visibility = INVISIBLE
        } else {
            text_view_cart_empty.visibility = INVISIBLE
            text_view_cart_empty.visibility = GONE
            text_view_cart_total_mrp.visibility = VISIBLE
            text_view_cart_total_price.visibility = VISIBLE
            text_view_cart_total_discount.visibility = VISIBLE
            btn_cart_checkout.visibility = VISIBLE
        }
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
