package com.example.moviesdemo.repository

import com.example.moviesdemo.network.BackEndApi
import com.example.moviesdemo.utils.Constants

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val backEndApi: BackEndApi,
) {
    suspend fun getUpcomingMovies() =
        backEndApi.getUpcomingMovies(Constants.API_KEY)

    suspend fun getDetailPageData(keyword: Int) =
        backEndApi.getDetailPage(keyword, Constants.API_KEY)

    suspend fun getSearchedMovies(queryKeyword: String) =
        backEndApi.getSearchedMovies(Constants.API_KEY, queryKeyword, 1)

}