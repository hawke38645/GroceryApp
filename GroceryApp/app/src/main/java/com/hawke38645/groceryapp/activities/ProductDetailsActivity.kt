package com.hawke38645.groceryapp.activities

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Config
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.Products
import com.hawke38645.groceryapp.models.Products.Companion.KEY_PRODUCT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class ProductDetailsActivity : AppCompatActivity() {

    var textViewCartCount: TextView? = null
    var databaseHelper = DatabaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product = intent.getSerializableExtra(KEY_PRODUCT) as Products
        init(product)
    }

    private fun init(products: Products) {
        this.toolbar("Details")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        text_view_product_detail_mrp.text = text_view_product_detail_mrp.text.toString() + products.mrp.toString()
        text_view_product_detail_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_product_detail_price.text = text_view_product_detail_price.text.toString() + products.price.toString()
        text_view_product_detail_desc.text = products.description

        Picasso
            .get()
            .load(Config.IMAGE_URL + products.image)
            .into(image_view_product_detail)
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

    private fun updateCartCount() {
        var itemCount = databaseHelper.getAllItemQuantity()
        if (itemCount == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = itemCount.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //ID of the back-button; it simply finishes the activity and returns home
            android.R.id.home -> {
                finish()
            }
            R.id.menu_profile -> {
                Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                toolbar("Cart")
                return true
            }
            R.id.menu_settings -> {
                Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}
