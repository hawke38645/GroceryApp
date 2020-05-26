package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.activities.ProductDetailsActivity
import com.hawke38645.groceryapp.app.Config
import com.hawke38645.groceryapp.database.DatabaseHelper
import com.hawke38645.groceryapp.helpers.UpdateCartIcon
import com.hawke38645.groceryapp.models.Products
import com.hawke38645.groceryapp.models.Products.Companion.KEY_PRODUCT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterProduct(var mContext: Context): RecyclerView.Adapter<AdapterProduct.ViewHolder>() {

    var mList: ArrayList<Products> = ArrayList()
    var databaseHelper = DatabaseHelper()
    private var listener: OnAdapterProductInteraction? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList[position]
        holder.bindView(product, position)
    }

    fun setData(list: ArrayList<Products>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(products: Products, position: Int) {
            itemView.text_view_product_name.text = products.productName
            itemView.text_view_product_mrp.text = "₹" + products.mrp.toString()
            itemView.text_view_product_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_product_price.text = "₹" + products.price.toString()
            itemView.text_view_product_item_quantity.text = databaseHelper.getCartItemQuantity(products._id).toString()

            if(databaseHelper.isItemInCart(products._id)) {
                itemView.btn_add_cart_item.visibility = INVISIBLE
                itemView.text_view_product_item_quantity.visibility = VISIBLE
                itemView.btn_plus_product.visibility = VISIBLE
                itemView.btn_minus_product.visibility = VISIBLE
            } else {
                itemView.btn_add_cart_item.visibility = VISIBLE
                itemView.text_view_product_item_quantity.visibility = INVISIBLE
                itemView.btn_plus_product.visibility = INVISIBLE
                itemView.btn_minus_product.visibility = INVISIBLE
            }

            Picasso
                .get()
                .load(Config.IMAGE_URL + products.image)
                .into(itemView.image_view_product)
            var updater = mContext as UpdateCartIcon
            itemView.setOnClickListener{
                var intent = Intent(mContext, ProductDetailsActivity::class.java)
                intent.putExtra(KEY_PRODUCT, products)
                mContext.startActivity(intent)
            }
            itemView.btn_add_cart_item.setOnClickListener{
                listener?.onItemClicked(it, position, products)
                updater.updateCartCount()
                notifyDataSetChanged()
            }
            itemView.btn_plus_product.setOnClickListener{
                listener?.onItemClicked(it, position, products)
                updater.updateCartCount()
                notifyDataSetChanged()
            }
            itemView.btn_minus_product.setOnClickListener{
                listener?.onItemClicked(it, position, products)
                updater.updateCartCount()
                notifyDataSetChanged()
            }
        }
    }

    interface OnAdapterProductInteraction {
        fun onItemClicked(view: View, position: Int, products: Products)
    }

    fun setOnAdapterProductInteractionListener(onAdapterProductInteraction: OnAdapterProductInteraction) {
        listener = onAdapterProductInteraction
    }

}