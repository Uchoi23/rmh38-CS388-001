package com.example.wishlistapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var products: MutableList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        products = mutableListOf()
        val productsRv = findViewById<RecyclerView>(R.id.productsRv)
        val adapter = productAdapter(products)
        productsRv.adapter = adapter
        productsRv.layoutManager = LinearLayoutManager(this)
        val productNameInput = findViewById<EditText>(R.id.productNameTxt)
        val productPriceInput = findViewById<EditText>(R.id.productPriceTxt)
        val productLinkInput = findViewById<EditText>(R.id.productLinkTxt)
        val submitButton = findViewById<Button>(R.id.submitBtn)
        var productName: String
        var productPrice: Double
        var productLink: String

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submitButton.setOnClickListener{
            productName = productNameInput.text.toString()
            productPrice = productPriceInput.text.toString().toDoubleOrNull() ?: 0.0
            productLink = productLinkInput.text.toString()
            val product = Product(productName,productPrice, productLink)
            products.add(product)
            adapter.notifyItemInserted(products.size - 1)
        }

    }
}