package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.helpers.SessionManager
import com.hawke38645.groceryapp.models.Address
import kotlinx.android.synthetic.main.row_address_layout.view.*

class AdapterAddress(var mContext: Context) : RecyclerView.Adapter<AdapterAddress.ViewHolder>() {

    var mList: ArrayList<Address> = ArrayList()
    private var listener: OnAdapterAddressInteraction? = null
    private var sessionManager = SessionManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_address_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var address = mList[position]
        holder.bindView(address, position)
    }

    fun setData(list: ArrayList<Address>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(address: Address, position: Int) {
            itemView.text_view_address_city.text = address.city
            itemView.text_view_address_location.text = address.location
            itemView.text_view_address_mobile.text = address.mobile
            itemView.text_view_address_name.text = address.name
            itemView.text_view_address_pincode.text = address.pincode.toString()
            itemView.text_view_address_street_name.text = address.streetName

            itemView.btn_delete_address_item.setOnClickListener{
                listener?.onItemClicked(itemView, position, address)
            }
            itemView.setOnClickListener {
                listener?.onItemClicked(itemView, position, address)
            }
        }
    }

    interface OnAdapterAddressInteraction {
        fun onItemClicked(view: View, position: Int, address: Address)
    }

    fun setOnAdapterAddressInteractionListener(onAdapterAddressInteraction: OnAdapterAddressInteraction) {
        listener = onAdapterAddressInteraction
    }

}