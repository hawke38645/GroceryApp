package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.app.Config
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.models.CartItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class AdapterCart(var mContext: Context): RecyclerView.Adapter<AdapterCart.ViewHolder>() {

    private var mList: ArrayList<CartItem> = ArrayList()
    var databaseHelper = DatabaseHelper()
    private var listener: OnAdapterCartInteraction? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cartItem = mList[position]
        holder.bindView(cartItem, position)
    }

    fun setData(list: ArrayList<CartItem>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(cartItem: CartItem, position: Int) {
            itemView.text_view_cart_item_name.text = cartItem.productName
            itemView.text_view_cart_item_price.text = "₹" + cartItem.price.toString()
            itemView.text_view_cart_item_mrp.text = "₹" + cartItem.mrp.toString()
            itemView.text_view_cart_item_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_cart_item_quantity.text = "Quantity: " + cartItem.quantity.toString()
            Picasso
                .get()
                .load(Config.IMAGE_URL + cartItem.imageURL)
                .into(itemView.image_view_cart_item)

            itemView.btn_delete_cart_item.setOnClickListener {
                listener?.onItemClicked(it, position)
            }
            itemView.btn_plus_cart_item.setOnClickListener {
                listener?.onItemClicked(it, position)
            }
            itemView.btn_minus_cart_item.setOnClickListener {
                listener?.onItemClicked(it, position)
            }
        }
    }

    interface OnAdapterCartInteraction {
        fun onItemClicked(view: View, position: Int)
    }

    fun setOnAdapterCartInteractionListener(onAdapterCartInteraction: OnAdapterCartInteraction) {
      listener = onAdapterCartInteraction
    }

}