package com.hawke38645.groceryapp.activities


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.fragments.FragmentSearch
import com.hawke38645.groceryapp.fragments.HomeFragment
import com.hawke38645.groceryapp.fragments.ProfileFragment
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class CategoryActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    var textViewCartCount: TextView? = null
    private var databaseHelper = DatabaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sessionManager = SessionManager()
//        if (!sessionManager.isLoggedIn()) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
        init()
    }

    private fun init() {
        this.toolbar("Category")
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_main, HomeFragment()).commit()
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        this.toolbar("Category")
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
            textViewCartCount?.visibility = GONE
        } else {
            textViewCartCount?.visibility = VISIBLE
            textViewCartCount?.text = itemCount.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(HomeFragment())

                toolbar("Category")
                return true
            }
            R.id.navigation_search -> {
                loadFragment(FragmentSearch())
                toolbar("Search")
                return true
            }
            R.id.navigation_profile -> {
                loadFragment(ProfileFragment())
                toolbar("Profile")
                return true
            }
            R.id.navigation_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                toolbar("Cart")
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment).commit()
    }

}
