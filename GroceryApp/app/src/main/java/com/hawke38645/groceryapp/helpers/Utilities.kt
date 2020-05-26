package com.hawke38645.groceryapp.helpers

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_bar.*

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun AppCompatActivity.toolbar(title:String?) {
    val myToolbar = toolbar
    myToolbar.title = title
    setSupportActionBar(myToolbar)
}