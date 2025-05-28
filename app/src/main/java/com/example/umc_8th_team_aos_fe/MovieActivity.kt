package com.example.umc_8th_team_aos_fe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_8th_team_aos_fe.databinding.ActivityMovieBinding
import com.example.umc_8th_team_aos_fe.databinding.LayoutReviewInputBinding
import kotlinx.coroutines.launch

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val movieId: Int by lazy { intent.getIntExtra("movieId", -1) }
    private var isExpanded = false
    private var expandBinding: LayoutReviewInputBinding? = null

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

        setProfileClickListener()

        if (movieId != -1) {
            fetchMovieDetail()
            fetchMovieReviews()
        }

        setLikeButton()
        //initView()

        binding.movieInputReviewET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && !isExpanded) {
                showExpandedReviewUI()
            }
        }
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
                        binding.movieToolTitleTV.text = it.moviename
                        binding.movieTitleTV.text = it.moviename
                        binding.movieSumTV.text = it.content
                        binding.movieCreditsTV.text = "감독: ${it.director}\n출연진: ${it.actor}"
                        setRatingBar(it.score.toInt())

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

    private fun showExpandedReviewUI() {
        val inflater = LayoutInflater.from(this)
        val expandBindingTemp = LayoutReviewInputBinding.inflate(inflater)

        // 확장 레이아웃을 컨테이너에 추가
        binding.movieInputReviewFL.addView(expandBindingTemp.root)
        expandBinding = expandBindingTemp
        isExpanded = true

        // 글자 수 변경 감지
        expandBinding?.reviewLayoutInputET?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val count = s?.length ?: 0
                expandBinding?.reviewLayoutNumTV?.text = "$count/300"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        expandBinding?.reviewLayoutInputBtn?.setOnClickListener {
            val text = expandBinding?.reviewLayoutInputET?.text.toString()
            val spoiler = expandBinding?.reviewLayoutToggle?.isChecked

            val sharedPref = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", null)

            if (text.isEmpty()) {
                Toast.makeText(this, "내용과 태그를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (token == null) {
                Toast.makeText(this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = ReviewRequest(
                movie_id = movieId,
                rating = binding.movieReviewRB.rating.toInt(),
                content = text,
                spoiler = spoiler == true,
                point_ids = listOf()
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.postReview(request, token)
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(this@MovieActivity, "리뷰 등록 완료!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@MovieActivity, "리뷰 등록 실패: 서버 오류", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MovieActivity, "리뷰 등록 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setRatingBar(score: Int) {
        when (score) {
            0 -> binding.movieRatingIV.setImageResource(R.drawable.rating0)
            1 -> binding.movieRatingIV.setImageResource(R.drawable.rating1)
            2 -> binding.movieRatingIV.setImageResource(R.drawable.rating2)
            3 -> binding.movieRatingIV.setImageResource(R.drawable.rating3)
            4 -> binding.movieRatingIV.setImageResource(R.drawable.rating4)
            5 -> binding.movieRatingIV.setImageResource(R.drawable.rating5)
        }
    }

    private fun setProfileClickListener() {
        binding.movieToolProfileIV.setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
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