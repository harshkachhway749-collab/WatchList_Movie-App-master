package com.example.watchlistmovieapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.watchlistmovieapp.data.Entity.WatchMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface  WatchMovieDao {

    @Insert
    suspend fun insertMovie(movie: WatchMovie)

    @Delete
    suspend fun deleteMovie(movie: WatchMovie)

    @Query("SELECT EXISTS(SELECT 1 FROM WatchMovie WHERE tmdbId = :tmdbId)")
    suspend fun isMovieInWatchlist(tmdbId: Int): Boolean


    @Query("SELECT * FROM WatchMovie")
     fun getAllmovies(): Flow<List<WatchMovie>>
}