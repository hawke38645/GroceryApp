package com.hawke38645.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hawke38645.groceryapp.fragments.DisplayFragment
import com.hawke38645.groceryapp.models.SubCategory

class AdapterTabLayout(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private var mFragmentList: ArrayList<Fragment> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
        }

    fun addFragment(subCategory: SubCategory) {
        mFragmentList.add(DisplayFragment.newInstance(subCategory.subId))
        mTitle.add(subCategory.subName)
    }
}


