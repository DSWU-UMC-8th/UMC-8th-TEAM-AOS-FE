package com.example.umc_8th_team_aos_fe

data class MovieListResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<Movie>
)
