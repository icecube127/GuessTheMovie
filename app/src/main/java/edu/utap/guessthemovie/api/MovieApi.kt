package edu.utap.guessthemovie.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    // example API call
    // http://www.omdbapi.com/?t=Toy+Story+4&apikey=b39cee
    // apikey is b39cee
    @GET("?&apikey=b39cee")
    suspend fun getMovie(@Query("t") title: String) : MovieData

    companion object Factory{
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("omdbapi.com")
            .build()

        fun create(): MovieApi = create(url)
        private fun create(httpUrl: HttpUrl): MovieApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }
    }
}