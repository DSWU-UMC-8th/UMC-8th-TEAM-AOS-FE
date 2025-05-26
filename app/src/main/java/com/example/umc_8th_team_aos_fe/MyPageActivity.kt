package com.example.umc_8th_team_aos_fe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_8th_team_aos_fe.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initReviewRV()
    }

    private fun initReviewRV() {
        val reviews = arrayListOf(
            Review("movie1", "1", "영화내용내용내용", 8),
            Review("movie1", "1", "영화내용용내용", 5),
            Review("movie1", "1", "영내용내용내용", 7)
        )

        binding.mypageReviewRV.layoutManager = LinearLayoutManager(this)
        binding.mypageReviewRV.adapter = MyReviewRVAdapter(this, reviews)
    }
}