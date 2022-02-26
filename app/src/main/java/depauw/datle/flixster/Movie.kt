package depauw.datle.flixster

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import org.json.JSONArray

data class Movie (
    val id: Int,
    val overview: String,
    val title: String,
    private val posterPath: String,
    private val backdropPath: String,
    val vote: Double
) {
    val posterPathURL = "https://image.tmdb.org/t/p/w342/$posterPath"
    val backdropURL = "https://image.tmdb.org/t/p/w780/$backdropPath"
    companion object {
        fun fromJSONtoList(movieJSONArray: JSONArray): List<Movie> {
            val movieList = mutableListOf<Movie>()
            for (i in 0 until movieJSONArray.length()) {
                val movieJSON = movieJSONArray.getJSONObject(i)
                movieList.add(
                    Movie(
                        movieJSON.getInt("id"),
                        movieJSON.getString("overview"),
                        movieJSON.getString("title"),
                        movieJSON.getString("poster_path"),
                        movieJSON.getString("backdrop_path"),
                        movieJSON.getDouble("vote_average")
                    )
                )
            }
            return movieList
        }
    }
}