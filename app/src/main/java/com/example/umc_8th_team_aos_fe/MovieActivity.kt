package com.example.umc_8th_team_aos_fe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_8th_team_aos_fe.databinding.ActivityMainBinding
import com.example.umc_8th_team_aos_fe.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        val title = intent.getStringExtra("movieTitle")
        val poster = intent.getIntExtra("moviePoster", R.drawable.poster_sample)

        binding.movieTitleTV.text = title
        binding.moviePosterIV.setImageResource(poster)

        val reviews = arrayListOf(
            Review("movie1", "1", "영화내용내용내용", 8),
            Review("movie1", "1", "영화내용용내용", 5),
            Review("movie1", "1", "영내용내용내용", 7)
        )

        binding.movieReviewRV.layoutManager = LinearLayoutManager(this)
        binding.movieReviewRV.adapter = MovieReviewAdapter(this, reviews)
    }
}