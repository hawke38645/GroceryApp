package com.hawke38645.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterAddress
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.helpers.toolbar
import com.hawke38645.groceryapp.models.Address
import com.hawke38645.groceryapp.models.AddressList
import kotlinx.android.synthetic.main.activity_address_display.*

class AddressDisplayActivity : AppCompatActivity(), AdapterAddress.OnAdapterAddressInteraction {

    var mList : ArrayList<Address> = ArrayList()
    private var sessionManager = SessionManager()
    private lateinit var adapterAddress: AdapterAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_display)

        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }
    private fun init() {
        toolbar("Select an Address")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_add_new_address.setOnClickListener{
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        btn_goto_payment.setOnClickListener{
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        recycler_view_address_display.layoutManager = LinearLayoutManager(this)
        adapterAddress = AdapterAddress(this)
        adapterAddress.setOnAdapterAddressInteractionListener(this)
        recycler_view_address_display.adapter = adapterAddress
        recycler_view_address_display.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        getAddresses(sessionManager.getUserId()!!)
    }

    private fun getAddresses(userId: String) {
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, Endpoints.getAddressesByUserId(userId),
            Response.Listener {
                var gson = GsonBuilder().create()
                var addressList = gson.fromJson(it.toString(), AddressList::class.java)
                mList = addressList.data
                //Toast.makeText(this, "" + mList.size, Toast.LENGTH_SHORT).show()
                adapterAddress.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        requestQueue.add(stringRequest)
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

    override fun onItemClicked(view: View, position: Int, address: Address) {
        view.setOnClickListener {
            sessionManager.setPrimaryAddress(address)
            Toast.makeText(this, "Primary address has been set!", Toast.LENGTH_SHORT).show()
        }
        when(view.id) {
            R.id.btn_delete_address_item -> {
                //TODO: Make a call to the API to DELETE the address in the clicked view (need to find a way to get "_id" to make the call
            }
        }

    }

}
