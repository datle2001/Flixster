package depauw.datle.flixster

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieItemAdapter(private val context: Context, private val movieList: List<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val isPopular = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        if(viewType == isPopular) {
            val contactView = inflater.inflate(R.layout.popular_movie_item, parent, false)
            viewHolder = ViewHolderPopular(contactView)
        } else {
            val contactView = inflater.inflate(R.layout.movie_item, parent, false)
            viewHolder = ViewHolder(contactView)
        }
        // Return a new holder instance
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList[position]
        if(holder.itemViewType == isPopular) {
            (holder as ViewHolderPopular).bind(movie)
        }
        else {
            (holder as ViewHolder).bind(movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(movieList[position].vote > 5) return isPopular
        return 0
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val posterView: ImageView = itemView.findViewById(R.id.moviePoster)
        private val titleView: TextView = itemView.findViewById(R.id.movieTitle)
        private val overviewView: TextView = itemView.findViewById(R.id.movieOverview)

        fun bind(movie: Movie) {
            overviewView.text = movie.overview
            titleView.text = movie.title
            val orientation = context.resources.configuration.orientation
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.posterPathURL).placeholder(R.drawable.image_loading).into(posterView)
            }
            else {
                Glide.with(context).load(movie.backdropURL).placeholder(R.drawable.image_loading).into(posterView)
            }
        }
    }

    inner class ViewHolderPopular(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val posterView: ImageView = itemView.findViewById(R.id.popularMoviePoster)
        fun bind(movie: Movie) {
            Glide.with(context).load(movie.backdropURL).placeholder(R.drawable.image_loading).into(posterView)
        }
    }
}
