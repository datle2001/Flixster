package depauw.datle.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val movieList = mutableListOf<Movie>()
    private lateinit var adapter: MovieItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = AsyncHttpClient()
        client.get(URL, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try{
                    val movieJSONArray = json.jsonObject.getJSONArray("results")
                    movieList.addAll(Movie.fromJSONtoList(movieJSONArray))
                    adapter.notifyDataSetChanged()
                } catch(e: JSONException) {
                    Log.e(TAG, "JSON exception $e")
                }
            }
        })
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = MovieItemAdapter(this, movieList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}