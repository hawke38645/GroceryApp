package com.hawke38645.groceryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterProduct
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.app.MyApplication
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.models.CartItem
import com.hawke38645.groceryapp.models.Products
import com.hawke38645.groceryapp.models.ProductList
import com.hawke38645.groceryapp.models.SubCategory.Companion.KEY_SUB_ID
import kotlinx.android.synthetic.main.fragment_display.*
import kotlinx.android.synthetic.main.fragment_display.view.*


class DisplayFragment : Fragment(), AdapterProduct.OnAdapterProductInteraction {

    private var subId: Int = 0
    private var mList: ArrayList<Products> = ArrayList()
    lateinit var adapterProduct: AdapterProduct
    private var databaseHelper = DatabaseHelper()
    private var mCartList: ArrayList<CartItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(KEY_SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_display, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.recycler_view_display_fragment.layoutManager = LinearLayoutManager(activity as Context)
        adapterProduct = AdapterProduct(activity as Context)
        adapterProduct.setOnAdapterProductInteractionListener(this)
        view.recycler_view_display_fragment.adapter = adapterProduct
        view.recycler_view_display_fragment.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        getProducts(subId)
    }

    override fun onItemClicked(view: View, position: Int, products: Products) {
        mCartList = databaseHelper.readCartItems()
        when(view.id) {
            R.id.btn_add_cart_item -> {
                databaseHelper.addCartItem(CartItem(mList[position]._id, mList[position].productName, mList[position].price, mList[position].mrp, mList[position].image, 1))
                Toast.makeText(MyApplication.instance, "${mList[position].productName} added to cart!", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_plus_product -> {
                databaseHelper.updateCartItem(databaseHelper.getCartItem(products.productName)!!, true)
            }
            R.id.btn_minus_product -> {
                databaseHelper.updateCartItem(databaseHelper.getCartItem(products.productName)!!, false)
            }
        }
    }

    private fun getProducts(subId: Int) {
        var stringRequest = StringRequest(Request.Method.GET, Endpoints.getProductsBySubId(subId),
        Response.Listener {
            progress_bar.visibility = View.GONE
            var gson = GsonBuilder().create()
            var prodlist = gson.fromJson(it.toString(), ProductList::class.java)
            mList = prodlist.data
            Toast.makeText(activity as Context, "" + mList.size, Toast.LENGTH_SHORT).show()
            adapterProduct.setData(mList)
        },
        Response.ErrorListener {
            Toast.makeText(activity as Context, it.message, Toast.LENGTH_SHORT).show()
            Log.d("ERROR", it.message.toString())
        })
        Volley.newRequestQueue(activity as Context).add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance(subId: Int) =
            DisplayFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_SUB_ID, subId)
                }
            }
    }
}
