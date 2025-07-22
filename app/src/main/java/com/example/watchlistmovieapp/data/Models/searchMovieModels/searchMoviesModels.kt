package com.example.watchlistmovieapp.data.Models.searchMovieModels

data class searchMoviesModels(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)