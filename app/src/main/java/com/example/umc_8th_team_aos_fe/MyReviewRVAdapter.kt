package com.example.umc_8th_team_aos_fe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_8th_team_aos_fe.databinding.ItemMyReviewBinding

class MyReviewRVAdapter(
    private val context: Context,
    private val reviews: ArrayList<Review>
): RecyclerView.Adapter<MyReviewRVAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMyReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Review) {
            binding.mypageTitleTV.text = item.reviewMovie
            binding.mypageReviewTV.text = item.reviewText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size
}