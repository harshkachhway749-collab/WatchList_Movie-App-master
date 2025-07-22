package com.example.watchlistmovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.watchlistmovieapp.data.Entity.WatchMovie
import com.example.watchlistmovieapp.data.dao.WatchMovieDao


@Database(entities = [WatchMovie::class], version = 1, exportSchema = false)
abstract class  WatchMovieDatabase: RoomDatabase() {


    abstract fun watchMovieDao(): WatchMovieDao



}