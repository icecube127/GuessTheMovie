package edu.utap.guessthemovie.api

import com.google.gson.annotations.SerializedName

data class MovieData (
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Rate")
    val rate: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String
)
