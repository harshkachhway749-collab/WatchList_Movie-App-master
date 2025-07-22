package com.example.watchlistmovieapp.data.Network

import com.example.watchlistmovieapp.CONSTS.API_KEY
import com.example.watchlistmovieapp.data.Models.PopularMovies.PopularMoviesModels
import com.example.watchlistmovieapp.data.Models.searchMovieModels.searchMoviesModels
import com.example.watchlistmovieapp.data.Models.trendingMoviesModel.trendingMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String= API_KEY
    ): Response<PopularMoviesModels>



    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey: String= API_KEY,
        @Query("query") query: String
    ):Response<searchMoviesModels>

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String= API_KEY
    ):Response<trendingMoviesModel>


}