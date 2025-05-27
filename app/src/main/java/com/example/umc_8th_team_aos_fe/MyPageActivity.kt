package com.example.umc_8th_team_aos_fe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_8th_team_aos_fe.databinding.ActivityMyPageBinding
import android.content.Context

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
        checkLogin()
        //initReviewRV()
    }

    private fun checkLogin() {
        val sharedPref = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token == null) {
            binding.mypageNickTV.text = "로그인"
            binding.mypageNickTV.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            return
        }

        val nickname = sharedPref.getString("nickname", "")
        val username = sharedPref.getString("username", "")

        binding.mypageNickTV.text = nickname
        binding.mypageIdTV.text = username
    }

//    private fun initReviewRV() {
//        val reviews = arrayListOf(
//            Review("movie1", "1", "영화내용내용내용", 8),
//            Review("movie1", "1", "영화내용용내용", 5),
//            Review("movie1", "1", "영내용내용내용", 7)
//        )
//
//        binding.mypageReviewRV.layoutManager = LinearLayoutManager(this)
//        binding.mypageReviewRV.adapter = MyReviewRVAdapter(this, reviews)
//    }
}