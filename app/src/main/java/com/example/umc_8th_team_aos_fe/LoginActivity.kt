package com.example.umc_8th_team_aos_fe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.umc_8th_team_aos_fe.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.loginSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun login() {
        val username = binding.loginIdET.text.toString()
        val password = binding.loginPwET.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    if (loginResponse.isSuccess == "true") {
                        Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        saveSPF(loginResponse.result, loginResponse.token)
                        finish()

                    } else {
                        Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "로그인 실패: 서버 오류", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSPF(user: User, token: String) {
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("token", token)
            putString("userId", user.userId)
            putString("username", user.username)
            putString("email", user.email)
            putString("nickname", user.nickname)
            apply()
        }
    }

//    private fun startMainActivity(loginResponse: LoginResponse) {
//        val user = loginResponse.result
//        val token = loginResponse.token
//
//        saveSPF(user, token)
//
//        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
//            putExtra("userId", user.userId)
//            putExtra("username", user.username)
//            putExtra("email", user.email)
//            putExtra("nickname", user.nickname)
//            putExtra("token", token)
//        }
//
//        startActivity(intent)
//        finish()
//    }
}