package com.example.umc_8th_team_aos_fe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_8th_team_aos_fe.databinding.ItemMovieReviewBinding

class MovieReviewAdapter (
    private val context: Context,
    private val reviews: List<Review>
): RecyclerView.Adapter<MovieReviewAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMovieReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            setRatingBar(binding, item.rating.toInt())
            binding.movieReviewTV.text = item.content
        }
    }

    private fun setReviewText(binding: ItemMovieReviewBinding, item: Review) {
        TODO("서버에서 spoiler 값을 같이 주게 되면 visible 속성 수정")
    }

    private fun setRatingBar(binding: ItemMovieReviewBinding, score: Int) {
        when (score) {
            0 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating0)
            1 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating1)
            2 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating2)
            3 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating3)
            4 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating4)
            5 -> binding.movieReviewRatingIV.setImageResource(R.drawable.rating5)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size
}