package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailsActivity"

class DetailsActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var ratingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_details)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        descriptionTextView = findViewById(R.id.mediaDescription)
        ratingTextView = findViewById(R.id.mediaRanking)
        // TODO: Get the extra from the Intent
        val selectedShow = intent.getSerializableExtra(SHOW_EXTRA) as tvShow
        val fullUrl = "https://image.tmdb.org/t/p/w500/" + selectedShow.showImageUrl

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = selectedShow.title
        descriptionTextView.text = selectedShow.description
        ratingTextView.text = selectedShow.rating.toString()

        // TODO: Load the media image
        Glide.with(this)
            .load(fullUrl)
            .into(mediaImageView)
    }
}