package com.example.flixster

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class tvShow(
    @SerializedName("original_name")
    var title: String? = null,

    @SerializedName("overview")
    var description: String? = null,

    @SerializedName("poster_path")
    var showImageUrl: String? = null,

    @SerializedName("popularity")
    var popularity: Double? = null,

    @SerializedName("vote_average")
    var rating: Double? = null
) : Serializable // Make tvShow implement Serializable
