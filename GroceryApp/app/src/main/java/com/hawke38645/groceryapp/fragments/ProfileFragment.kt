package com.hawke38645.groceryapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.activities.LoginActivity
import com.hawke38645.groceryapp.activities.OrderDisplayActivity
import com.hawke38645.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        initView(view)
        return view
    }


    private fun initView(view: View) {
        var sessionManager = SessionManager()
        val user = sessionManager.getUser()
        val primaryAddress = sessionManager.getPrimaryAddress()

        view.text_view_profile_title.text = "Welcome, ${user.name}!"
        view.text_view_name_label.text = "Name: ${user.name}"
        view.text_view_email_label.text = "Email: ${user.email}"
        view.text_view_mobile_label.text = "Mobile: ${user.mobile}"
        view.text_view_street_name_label.text = "Street Name: ${primaryAddress.streetName}"
        view.text_view_city_name_label.text = "City: ${primaryAddress.city}"
        view.text_view_home_number_label.text = "Home No: ${primaryAddress.houseNo}"
        view.text_view_location_label.text = "Location: ${primaryAddress.location}"
        view.text_view_clickable_order_view.text = "View My Orders"


        view.btn_logout.setOnClickListener{
            sessionManager.logout()
            activity?.startActivity(Intent(activity, LoginActivity::class.java))
        }
        view.text_view_clickable_order_view.setOnClickListener{
            activity?.startActivity(Intent(activity, OrderDisplayActivity::class.java))
        }
    }

}
