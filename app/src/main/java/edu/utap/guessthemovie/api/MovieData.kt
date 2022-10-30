package edu.utap.guessthemovie.api

import com.google.gson.annotations.SerializedName

data class MovieData (
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String
)
