package com.example.umc_8th_team_aos_fe

data class SignupRequest(
    val username: String,
    val password: String,
    val passwordConfirm: String,
    val email: String,
    val nickname: String
)

data class SignupResponse(
    val isSuccess: String,
    val code: String,
    val message: String
)

data class CheckIdResponse(
    val isSuccess: String,
    val code: String,
    val message: String
)
