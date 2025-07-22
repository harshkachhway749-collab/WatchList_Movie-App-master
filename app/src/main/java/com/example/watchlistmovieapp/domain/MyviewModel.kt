package com.example.watchlistmovieapp.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlistmovieapp.Repo.Repo
import com.example.watchlistmovieapp.data.Entity.WatchMovie

import com.example.watchlistmovieapp.data.Models.PopularMovies.PopularMoviesModels
import com.example.watchlistmovieapp.data.Models.searchMovieModels.searchMoviesModels
import com.example.watchlistmovieapp.data.Models.trendingMoviesModel.trendingMoviesModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(
    private val repo: Repo
) : ViewModel() {





    val trendingMovies = mutableStateOf<trendingMoviesModel?>(null)
    val popularMovies = mutableStateOf<PopularMoviesModels?>(null)
    val searchMovies = mutableStateOf<searchMoviesModels?>(null)


    private val _state= MutableStateFlow<Appstate>(Appstate())

    val state=_state.value

    private val _moviesFlow = MutableStateFlow<List<WatchMovie>>(emptyList())
    val moviesFlow: StateFlow<List<WatchMovie>> = _moviesFlow.asStateFlow()


    suspend fun isMovieInWatchlist(tmdbId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            repo.isMovieInWatchlist(tmdbId)
        }
    }







    fun addMovie(movie: WatchMovie){
        viewModelScope.launch {
            repo.insertMovie(movie)
        }
    }

    fun deleteMovie(movie: WatchMovie){
        viewModelScope.launch {
            repo.deleteMovie(movie)
        }
    }


    init {
        getTrendingMovies()
        getPopularMovies()
        searchMovie(query = "inception")

        viewModelScope.launch {
            repo.allmovies.collect { movies ->
                _moviesFlow.value = movies
            }
        }

    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                val response = repo.getTrendingMovies()
                Log.d("TAG1", "Raw JSON: ${response.body().toString()}")
                Log.d("TAG1", "getTrendingMovies: Response Code: ${response.code()}")
                Log.d("TAG1", "getTrendingMovies: Is Successful: ${response.isSuccessful}")
                Log.d("TAG1", "getTrendingMovies: Body: ${response.body()}")
                if (response.isSuccessful) {
                    Log.d("TAG1", "getTrendingMovies: ${response.body()?.results}")
                    withContext(Dispatchers.Main) {
                        trendingMovies.value = response.body()
                        Log.d("TAG1", "getTrendingMovies: ${trendingMovies.value?.results}")
                    }
                } else {
                    Log.d("TAG1", "Error: ${response.errorBody()?.string()}")

                }
            } catch (e: Exception) {
                Log.d("TAG1", "getTrendingMovies: ${e.message}")
            }
        }
    }


    fun getPopularMovies(){
        viewModelScope.launch {
            try{
                val response=repo.getPopularMovies()
                Log.d("TAG2", "Raw JSON: ${response.body().toString()}")
                Log.d("TAG2", "getpopularMovies: Response Code: ${response.code()}")
                Log.d("TAG2", "getpopularMovies: Is Successful: ${response.isSuccessful}")
                if(response.isSuccessful){
                    Log.d("TAG2", "getpopularMovies: ${response.body()?.results}")
                    withContext(Dispatchers.Main){
                        popularMovies.value=response.body()
                        Log.d("TAG2", "getpopularMovies: ${popularMovies.value?.results}")
                    }
                }else{
                    Log.d("TAG2", "Error: ${response.errorBody()?.string()}")
                }
            }catch (e:Exception){
                Log.d("TAG2", "getpopularMovies: ${e.message}")
            }
        }
    }

    fun searchMovie(
        query:String
    ){
        viewModelScope.launch {
            try {
                val response=repo.searchMovies(query=query)
                Log.d("TAG3", "Raw JSON: ${response.body().toString()}")
                Log.d("TAG3", "searchMovie: Response Code: ${response.code()}")
                Log.d("TAG3", "searchMovie: Is Successful: ${response.isSuccessful}")
                if(response.isSuccessful){
                    Log.d("TAG3", "searchMovie: ${response.body()?.results}")
                    withContext(Dispatchers.Main){
                       searchMovies.value=response.body()
                        Log.d("TAG3", "searchMovie: ${popularMovies.value?.results}")
                    }
                }
                else{
                    Log.d("TAG3", "Error: ${response.errorBody()?.string()}")
                }
            }catch (e:Exception){
                Log.d("TAG3", "searchMovie: ${e.message}")

            }
        }
    }




}

data class Appstate(
    var id:MutableState<Int> = mutableStateOf(0),
    val Loading : Boolean= false,
    val  allContact:List<WatchMovie> = emptyList(),
    val Error: String="",
    var title: MutableState<String> = mutableStateOf(""),
    var description:MutableState<String> = mutableStateOf(""),



    )