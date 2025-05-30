package com.example.umc_8th_team_aos_fe

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/home/recommended")
    suspend fun getRecommendedMovies(): Response<MovieListResponse>

    @GET("/api/home/now-playing")
    suspend fun getNowPlayingMovies(): Response<MovieListResponse>

    @GET("/api/home/top-rated")
    suspend fun getTopRatedMovies(): Response<MovieListResponse>

    @GET("/api/movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): Response<MovieDetailResponse>

    @GET("/api/movie/{movieId}/reviews")
    suspend fun getMovieReviews(@Path("movieId") movieId: Int): Response<List<Review>>

    @POST("/api/movie/{movieId}/like")
    suspend fun likeMovie(@Path("movieId") movieId: Int): Response<LikeResponse>

    @GET("/api/check-id")
    suspend fun checkDuplicateId(@Query("username") username: String): Response<CheckIdResponse>

    @POST("/api/register")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("/reviews")
    fun postReview(@Body review: ReviewRequest, @Header("Authorization") token: String): Response<ResponseBody>
}