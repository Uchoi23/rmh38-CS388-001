package com.example.wishlistapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class productAdapter(private val products: MutableList<Product>): RecyclerView.Adapter<productAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productNameTextView: TextView
        val productPriceTextView: TextView
        val productLinkTextView: TextView

        init {
             productNameTextView = itemView.findViewById(R.id.productNameItem)
             productPriceTextView = itemView.findViewById(R.id.productPriceItem)
             productLinkTextView = itemView.findViewById(R.id.productLinkItem)

            productLinkTextView.setOnClickListener{
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(productLinkTextView.text.toString()))
                    ContextCompat.startActivity(it.context, browserIntent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(it.context, "Invalid URL for " + productNameTextView.text.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.product_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.productNameTextView.text = product.productName
        holder.productPriceTextView.text = product.productPrice.toString()
        holder.productLinkTextView.text = product.productLink
    }
}