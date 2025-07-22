package com.example.watchlistmovieapp.Repo


import com.example.watchlistmovieapp.data.Entity.WatchMovie
import com.example.watchlistmovieapp.data.Models.PopularMovies.PopularMoviesModels
import com.example.watchlistmovieapp.data.Models.searchMovieModels.searchMoviesModels
import com.example.watchlistmovieapp.data.Models.trendingMoviesModel.trendingMoviesModel
import com.example.watchlistmovieapp.data.Network.ApiProvider
import com.example.watchlistmovieapp.data.dao.WatchMovieDao

import kotlinx.coroutines.flow.Flow
import retrofit2.Response


class Repo (
    private val watchMovieDao: WatchMovieDao


){


    suspend fun getTrendingMovies(): Response<trendingMoviesModel> {
        return ApiProvider.apiprovider().getTrendingMovies()


    }
    suspend fun getPopularMovies(): Response<PopularMoviesModels> {
        return ApiProvider.apiprovider().getPopularMovies()
    }

    suspend fun searchMovies(
        query: String
    ): Response<searchMoviesModels>{
        return ApiProvider.apiprovider().getSearchMovies(query=query)
    }


    suspend fun insertMovie(movie: WatchMovie)=watchMovieDao.insertMovie(movie)

    suspend fun deleteMovie(movie: WatchMovie)=watchMovieDao.deleteMovie(movie)

    val allmovies: Flow<List<WatchMovie>> =watchMovieDao.getAllmovies()

    suspend fun isMovieInWatchlist(tmdbId: Int): Boolean = watchMovieDao.isMovieInWatchlist(tmdbId)








}