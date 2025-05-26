package com.example.umc_8th_team_aos_fe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_8th_team_aos_fe.databinding.ItemMovieBinding

class MainVPAdapter (
    private val context: Context,
    private val movies: List<Movie>
): RecyclerView.Adapter<MainVPAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.itemMovieTV.text = item.title
            //binding.itemMovieIV.setImageResource(item.posterUrl)
            Glide.with(context).load(item.posterUrl).placeholder(R.drawable.poster_sample).into(binding.itemMovieIV)

            binding.root.setOnClickListener{
                val intent = Intent(context, MovieActivity::class.java).apply {
                    putExtra("movieId", item.movieId)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}