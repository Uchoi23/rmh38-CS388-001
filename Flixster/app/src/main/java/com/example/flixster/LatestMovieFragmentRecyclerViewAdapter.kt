package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id

class LatestMovieFragmentRecyclerViewAdapter(
    private val movies: List<LatestMovie>,
    private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<LatestMovieFragmentRecyclerViewAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_latest_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: LatestMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
        ) {
        val movie = movies[position]
        val fullUrl = "https://image.tmdb.org/t/p/w500/" + movie.movieImageUrl
        holder.mItem = movie
        holder.mMovieTitle.text = movie.title.toString()
        holder.mMovieDescription.text = movie.description.toString()

        Glide.with(holder.mView)
            .load(fullUrl)
            .centerInside()
            .into(holder.mMovieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}