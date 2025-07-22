package com.example.watchlistmovieapp.data.database

import android.content.Context
import androidx.room.Room
import com.example.watchlistmovieapp.data.dao.WatchMovieDao

object DatabaseProvider {

    private var Instance: WatchMovieDatabase?=null


    fun getdatabase(context: Context): WatchMovieDatabase{
        return Instance?:synchronized(this) {
            val instance=Room.databaseBuilder(
                context.applicationContext,
                WatchMovieDatabase::class.java,
                "watchList.db"
            ).build()
            Instance=instance
            instance

        }
    }

    fun getWatchListDao(context: Context): WatchMovieDao{
        return getdatabase(context=context).watchMovieDao()

    }

}