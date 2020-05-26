package com.hawke38645.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hawke38645.groceryapp.R
import kotlinx.android.synthetic.main.activity_thank_you.*

class ThankYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        init()
    }

    private fun init() {
        btn_goto_home.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }
    }
}
