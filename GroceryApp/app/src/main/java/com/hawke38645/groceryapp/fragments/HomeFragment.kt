package com.hawke38645.groceryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.adapters.AdapterCategory
import com.hawke38645.groceryapp.adapters.AdapterImages
import com.hawke38645.groceryapp.app.Endpoints
import com.hawke38645.groceryapp.models.Category
import com.hawke38645.groceryapp.models.CategoryList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.view_pager_home_images

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapterCategory: AdapterCategory
    var mList: ArrayList<Category> = ArrayList()
    //var mImageIds: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        var adapterImage = AdapterImages(activity as Context)
        view.view_pager_home_images.adapter = adapterImage

        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        view.recycler_view.layoutManager = GridLayoutManager(activity, 2)
        adapterCategory = AdapterCategory(activity!!)
        view.recycler_view.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        view.recycler_view.adapter = adapterCategory
        getCategories()
    }

    private fun getCategories() {
        var requestQueue = Volley.newRequestQueue(activity)
        var stringRequest = StringRequest(
            Request.Method.GET, Endpoints.getCategory(),
            Response.Listener {
                var gson = GsonBuilder().create()
                var categoryList = gson.fromJson(it.toString(), CategoryList::class.java)
                mList = categoryList.data
                Toast.makeText(activity, "" + mList.size, Toast.LENGTH_SHORT).show()
                adapterCategory.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                Log.d("ERROR", it.message.toString())
            })
        requestQueue.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
