package edu.utap.guessthemovie.api

class Repository (private val api: MovieApi) {
    // XXX Write me.

    // fetches data from API call and return it.
    suspend fun fetchMovie(title: String): MovieData{
        //return api.getMovie(title).results
        return api.getMovie().results
    }
}