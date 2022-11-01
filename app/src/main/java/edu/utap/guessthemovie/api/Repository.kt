package edu.utap.guessthemovie.api

class Repository (private val api: MovieApi) {
    // fetches data from API call and return it.
    suspend fun fetchMovie(title : String): MovieData {
        //return api.getMovie(title)
        return api.getMovie()
    }
}