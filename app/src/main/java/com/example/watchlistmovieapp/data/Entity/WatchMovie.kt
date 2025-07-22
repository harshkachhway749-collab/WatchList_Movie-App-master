package com.example.watchlistmovieapp.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WatchMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val tmdbId: Int,
    val title: String,
    val poster_path: String,
    val description: String,
    val rating: Double,
    val isAddedToWatchListDB: Boolean = true
)
