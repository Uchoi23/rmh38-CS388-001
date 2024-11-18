package com.example.BitFitPart1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.R

class EntryAdapter(private val mEntries: List<DisplayEntry>) : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val entryTitle = itemView.findViewById<TextView>(R.id.EntryTitleText)
        val entryDate = itemView.findViewById<TextView>(R.id.EntryDateText)
        val entryEntry = itemView.findViewById<TextView>(R.id.EntryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.entry_detail, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mEntries[position]
        holder.entryDate.text = entry.date.toString().substring(0, entry.date.toString().indexOf("T"))
        holder.entryEntry.text = entry.entry
        holder.entryTitle.text = entry.title.toString()
    }

    override fun getItemCount(): Int {
        return mEntries.size
    }
}