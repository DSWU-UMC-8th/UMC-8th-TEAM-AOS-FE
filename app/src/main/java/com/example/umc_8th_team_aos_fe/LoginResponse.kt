package com.example.umc_8th_team_aos_fe

data class LoginResponse(
    val isSuccess: String,
    val code: String,
    val message: String,
    val token: String,
    val result: User
)
