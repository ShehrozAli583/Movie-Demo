package com.example.moviesdemo.network

import com.example.moviesdemo.models.DetailPageModel
import com.example.moviesdemo.models.SearchedResultModel
import com.example.moviesdemo.models.UpcomingMoviesModel
import retrofit2.Response
import retrofit2.http.*

interface BackEndApi {

    @GET("3/movie/popular")
    suspend fun getUpcomingMovies(@Query("api_key") key : String): Response<UpcomingMoviesModel>

    @GET("3/movie/{keyword}")
    suspend fun getDetailPage(@Path("keyword") keyword: Int, @Query("api_key") key : String): Response<DetailPageModel>

    @GET("3/search/movie")
    suspend fun getSearchedMovies(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<SearchedResultModel>


}

