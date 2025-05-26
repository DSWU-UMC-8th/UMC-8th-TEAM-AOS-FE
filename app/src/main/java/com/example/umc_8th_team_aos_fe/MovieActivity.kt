package com.example.umc_8th_team_aos_fe

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_8th_team_aos_fe.databinding.ActivityMainBinding
import com.example.umc_8th_team_aos_fe.databinding.ActivityMovieBinding
import kotlinx.coroutines.launch

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val movieId: Int by lazy { intent.getIntExtra("movieId", -1) }

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

        if (movieId != -1) {
            fetchMovieDetail()
            fetchMovieReviews()
        }

        setLikeButton()
        //initView()
    }

    private fun setLikeButton() {
        binding.movieLikeBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.likeMovie(movieId)
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        // TODO: 추후 서버에서 추천 수(likes)를 내려주면 UI에 반영하도록 수정
                        // movieLikeTV.text = "${response.body()?.result?.likes}"
                        Toast.makeText(this@MovieActivity, "추천 성공", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MovieActivity, "추천 실패", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LikeMovie", "에러: ${e.message}")
                }
            }
        }

    }

    private fun fetchMovieDetail() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getMovieDetail(movieId)
                if (response.isSuccessful) {
                    val detail = response.body()?.reviews?.firstOrNull()
                    detail?.let {
                        binding.movieTitleTV.text = it.moviename
                        binding.movieSumTV.text = it.content
                        binding.movieCreditsTV.text = "감독: ${it.director}, 출연진: ${it.actor}"
                        binding.movieScoreTV.text = "평점: ${it.score}"

                        Glide.with(this@MovieActivity)
                            .load(it.movieImage)
                            .placeholder(R.drawable.poster_sample)
                            .into(binding.moviePosterIV)
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetail", "영화 상세 정보 불러오기 실패", e)
            }
        }
    }

    private fun fetchMovieReviews() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getMovieReviews(movieId)
                if (response.isSuccessful) {
                    val reviews = response.body() ?: emptyList()
                    binding.movieReviewRV.adapter = MovieReviewAdapter(this@MovieActivity, reviews)
                    binding.movieReviewRV.layoutManager = LinearLayoutManager(this@MovieActivity)
                }
            } catch (e: Exception) {
                Log.e("MovieDetail", "리뷰 불러오기 실패", e)
            }
        }
    }

//    private fun initView() {
//        val title = intent.getStringExtra("movieTitle")
//        val poster = intent.getIntExtra("moviePoster", R.drawable.poster_sample)
//
//        binding.movieTitleTV.text = title
//        binding.moviePosterIV.setImageResource(poster)
//
//        val reviews = arrayListOf(
//            Review("movie1", "1", "영화내용내용내용", 8),
//            Review("movie1", "1", "영화내용용내용", 5),
//            Review("movie1", "1", "영내용내용내용", 7)
//        )
//
//        binding.movieReviewRV.layoutManager = LinearLayoutManager(this)
//        binding.movieReviewRV.adapter = MovieReviewAdapter(this, reviews)
//    }
}