package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.hawke38645.groceryapp.R
import kotlinx.android.synthetic.main.image_slider_layout.view.*

class AdapterImages(var mContext: Context): PagerAdapter() {

    private var mImageIds: ArrayList<Int> =  arrayListOf(R.drawable.vegetables1, R.drawable.vegetables2, R.drawable.vegetables3)

    override fun getCount(): Int {
        return mImageIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var inflater = LayoutInflater.from(mContext)
        var view = inflater.inflate(R.layout.image_slider_layout, container,false)

        view.image_view_slider.setImageResource(mImageIds[position])
        container.addView(view)

        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViews(position,position)
    }
}