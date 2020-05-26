package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.models.Order
import kotlinx.android.synthetic.main.row_order_adapter.view.*

class AdapterOrder(var mContext: Context): RecyclerView.Adapter<AdapterOrder.ViewHolder>() {

    var mList: ArrayList<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = mList[position]
        holder.bindView(order, position)
    }

    fun setData(list: ArrayList<Order>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(order: Order, position: Int) {
            itemView.text_view_order_id.text = "Order ID: ${order._id}"
            itemView.text_view_order_total_payment.text = "Total: ₹${order.orderSummary?.totalAmount}"
            //itemView.text_view_order_user_name.text = "Name: ${order.users.name}"
            itemView.text_view_order_date.text = "Date: ${order.date}"
            itemView.text_view_order_city.text = "City: ${order.shippingAddress.city}"
        }
    }
}