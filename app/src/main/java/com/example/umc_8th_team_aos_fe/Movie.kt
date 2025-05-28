package com.example.umc_8th_team_aos_fe

data class Movie (
    val movieId: Int,
    val title: String,
    val posterUrl: String,
    val score: Double? = null
)

data class MovieDetailResponse(
    val movieId: Int,
    val totalReviews: Int,
    val page: Int,
    val size: Int,
    val reviews: List<MovieDetail>
)

data class MovieDetail(
    val movieId: Int,
    val moviename: String,
    val releaseDate: String,
    val age: String,
    val time: String,
    val content: String,
    val director: String,
    val score: Float,
    val actor: String,
    val movieImage: String
)

data class Review(
    val reviewId: Int,
    val userId: Int,
    val nickname: String,
    val rating: Float,
    val content: String,
    val created_at: String
)

data class ReviewRequest(
    val movie_id: Int,
    val rating: Int,
    val content: String,
    val spoiler: Boolean,
    val point_ids: List<Int>
)

data class LikeResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: LikeResult
)

data class LikeResult(
    val movieId: Int,
    val likes: Int
)

