package com.example.umc_8th_team_aos_fe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.umc_8th_team_aos_fe.databinding.ActivityLoginBinding
import com.example.umc_8th_team_aos_fe.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var isIdChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setDupCheckBtnClickListener()
    }

    private fun setDupCheckBtnClickListener() {
        binding.signupDupBtn.setOnClickListener {
            val username = binding.signupIdET.text.toString()

            if (username.isBlank()) {
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.checkDuplicateId(username)
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.message?.contains("사용 가능") == true) {
                            Toast.makeText(this@SignupActivity, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                            isIdChecked = true
                        } else {
                            Toast.makeText(this@SignupActivity, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show()
                            isIdChecked = false
                        }
                    } else {
                        Toast.makeText(this@SignupActivity, "중복 확인 실패", Toast.LENGTH_SHORT).show()
                        isIdChecked = false
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@SignupActivity, "오류: ${e.message}", Toast.LENGTH_SHORT).show()
                    isIdChecked = false
                }
            }
        }

        binding.signupIdTV.addTextChangedListener {
            isIdChecked = false
        }
    }

    private fun setSignupBtnClickListener() {
        binding.signupBtn.setOnClickListener {
            if (!isIdChecked) {
                Toast.makeText(this, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val username = binding.signupIdET.text.toString()
            val password = binding.signupPwET.text.toString()
            val passwordConfirm = binding.signupPwET2.text.toString()
            val email = binding.signupEmailET.text.toString()
            val nickname = binding.signupNickET.text.toString()

            if (password != passwordConfirm) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = SignupRequest(username, password, passwordConfirm, email, nickname)

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.signup(request)
                    if (response.isSuccessful && response.body()?.isSuccess == "true") {
                        Toast.makeText(this@SignupActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@SignupActivity, "회원가입 실패: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@SignupActivity, "오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}