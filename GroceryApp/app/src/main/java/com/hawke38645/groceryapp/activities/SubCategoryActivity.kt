package com.hawke38645.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterTabLayout
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.UpdateCartIcon
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.Category.Companion.KEY_CAT_ID
import com.hawke38645.groceryapp.models.SubCategory
import com.hawke38645.groceryapp.models.SubCategoryList
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class SubCategoryActivity : AppCompatActivity(), UpdateCartIcon {

    var catId = 0
    private var mList: ArrayList<SubCategory> = ArrayList()
    private lateinit var adapterTabLayout : AdapterTabLayout
    var textViewCartCount: TextView? = null
    var databaseHelper = DatabaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        catId = intent.getIntExtra(KEY_CAT_ID, 1)
        getSubCategory(catId)
        init()
    }

    private fun init() {
        this.toolbar("Subcategory")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapterTabLayout = AdapterTabLayout(supportFragmentManager)
    }

    private fun getSubCategory(catId: Int) {
        var stringRequest = StringRequest(
            Request.Method.GET, Endpoints.getSubCategoryByCatId(catId),
            Response.Listener{
                var gson = GsonBuilder().create()
                var subList = gson.fromJson(it.toString(), SubCategoryList::class.java)
                mList = subList.data
                for(i in 0 until mList.size) {
                    adapterTabLayout.addFragment(mList[i])
                }
                view_pager.adapter = adapterTabLayout
                tab_layout.setupWithViewPager(view_pager)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        Volley.newRequestQueue(this).add(stringRequest)

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

     override fun updateCartCount() {
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
