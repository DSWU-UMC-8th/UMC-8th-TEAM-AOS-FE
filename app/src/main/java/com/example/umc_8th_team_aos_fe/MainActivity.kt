package com.example.umc_8th_team_aos_fe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_8th_team_aos_fe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setProfile()

        initRecommendedVP()
        initReleasedVP()
        initHighratedVP()
    }

    private fun setProfile() {
        binding.mainProfileIV.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecommendedVP() {
        val movies = arrayListOf(
            Movie("Mad Max", R.drawable.poster_sample),
            Movie("John Wick", R.drawable.poster_sample),
            Movie("John ick", R.drawable.poster_sample),
            Movie("Jon Wick", R.drawable.poster_sample)
        )
        binding.recommendedRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedRV.adapter = MainVPAdapter(this, movies)
    }

    private fun initReleasedVP() {
        val movies = arrayListOf(
            Movie("Mad Max", R.drawable.poster_sample),
            Movie("John Wick", R.drawable.poster_sample)
        )
        binding.releasedRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.releasedRV.adapter = MainVPAdapter(this, movies)
    }

    private fun initHighratedVP() {
        val movies = arrayListOf(
            Movie("Mad Max", R.drawable.poster_sample),
            Movie("John Wick", R.drawable.poster_sample)
        )
        binding.highratedRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.highratedRV.adapter = MainVPAdapter(this, movies)
    }
}