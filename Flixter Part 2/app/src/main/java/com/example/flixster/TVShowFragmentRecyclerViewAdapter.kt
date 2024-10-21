package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id

const val SHOW_EXTRA = "SHOW_EXTRA"

class TVShowFragmentRecyclerViewAdapter(
    private val tvShows: List<tvShow>,
    private val mListener: tvShowFragment
) : RecyclerView.Adapter<TVShowFragmentRecyclerViewAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_tv_show, parent, false)
        return TVShowViewHolder(view, parent.context) // Pass context to ViewHolder
    }

    inner class TVShowViewHolder(val mView: View, private val context: Context) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        var mItem: tvShow? = null
        val mShowTitle: TextView = mView.findViewById<View>(id.show_title) as TextView
        val mShowDescription: TextView = mView.findViewById<View>(id.show_description) as TextView
        val mShowImage: ImageView = mView.findViewById<View>(id.show_image) as ImageView
        val mshowRating: TextView = mView.findViewById<View>(id.showRating) as TextView

        // Initialize the click listener
        init {
            mView.setOnClickListener(this)
        }

        // Handle the click event
        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val selectedShow = tvShows[position]
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(SHOW_EXTRA, selectedShow)
                context.startActivity(intent) // Start the activity
            }
        }
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val show = tvShows[position]
        val fullUrl = "https://image.tmdb.org/t/p/w500/" + show.showImageUrl
        holder.mItem = show
        holder.mShowTitle.text = show.title.toString()
        holder.mShowDescription.text = show.description.toString()
        holder.mshowRating.text = "⭐" + show.rating.toString()

        Glide.with(holder.mView)
            .load(fullUrl)
            .centerInside()
            .into(holder.mShowImage)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    // Listener interface for click events
    interface OnListFragmentInteractionListener {
        fun onItemClick(item: tvShow)
    }
}
