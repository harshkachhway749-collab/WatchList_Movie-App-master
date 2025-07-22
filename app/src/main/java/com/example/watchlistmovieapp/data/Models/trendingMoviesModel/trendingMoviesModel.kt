package com.example.watchlistmovieapp.data.Models.trendingMoviesModel

data class trendingMoviesModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)