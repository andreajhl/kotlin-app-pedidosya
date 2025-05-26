package com.example.kotlin_app_pedidosya.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_app_pedidosya.R
import com.example.kotlin_app_pedidosya.ui.fragments.ProductListFragment

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_list)

        supportFragmentManager.beginTransaction()
            .replace(R.id.product_list_fragment, ProductListFragment.newInstance(1))
            .commit()
    }
}