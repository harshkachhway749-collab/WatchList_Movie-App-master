package com.example.watchlistmovieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.watchlistmovieapp.Repo.Repo
import com.example.watchlistmovieapp.data.Models.trendingMoviesModel.Result
import com.example.watchlistmovieapp.data.database.DatabaseProvider
import com.example.watchlistmovieapp.presentation.Screens.MainScreenUI


import com.example.watchlistmovieapp.domain.MyViewModel

import com.example.watchlistmovieapp.presentation.ui.theme.WatchListMovieAppTheme

class MainActivity : ComponentActivity(

) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val dao = DatabaseProvider.getWatchListDao(this)
        // Create the repository
        val repository = Repo(dao)
        val viewModel= MyViewModel(repository)

        enableEdgeToEdge()
        setContent {
            WatchListMovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
////                    TrendingMoviesScreen(viewModel)
                    MainScreenUI(viewModel)
//                    HomeScreenUI(viewModel)
//                    homescreen(viewModel)
//                    WatchListScreen(viewModel)
//                    MovieApp(viewModel)

                }
            }
        }
    }
}

//
@Composable
fun TrendingMoviesScreen(viewModel: MyViewModel = viewModel()) {
    val movies = viewModel.trendingMovies.value
    Log.d("TAG", "TrendingMoviesScreen: ${movies?.results}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies?.results ?: emptyList()) { movie ->
            MovieCard(movie = movie)

        }
    }
}


@Composable
fun MovieCard(movie: Result) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
    {
        Column {

                Text(text = movie.title.toString(), fontSize = 24.sp)



                AsyncImage(
                    model="https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                    contentDescription = null,
                )

                Text(text = movie.release_date.toString(), fontSize = 16.sp)






        }
    }
}

//@Composable
//fun homescreen(viewModel: MyViewModel) {
////
//
//    val title= remember { mutableStateOf("")  }
//    val description = remember { mutableStateOf("") }
//
//
//
//    Box(
//        modifier = Modifier.padding(30.dp)
//    ){
//        Column {
//
//
//            OutlinedTextField(
//                value=title.value,
//                onValueChange = {title.value=it} ,
//                label = { Text(text = "title")  },
//                placeholder = {"ENTER THE TITLE FOR MOVIE"}
//            )
//            OutlinedTextField(
//                value=description.value,
//                onValueChange = {description.value=it} ,
//                label = { Text(text = "description")  },
//                placeholder = {"ENTER THE DESCRIPTION FOR MOVIE"}
//            )
//
//
//            Button(
//                onClick = {
//
//                    val title=title.value
//                    val description=description.value
//                    val movie=WatchMovie(
//                        id=0,
//                        title, description)
//
//                    if( title.isNotEmpty() && description.isNotEmpty()){
//                        viewModel.addMovie(movie)
//
//                    }
//
//                }
//            ) {
//                Text(text = "Add Movie")
//            }
//
//
//            val movies = viewModel.moviesFlow.collectAsState()
//
//            LazyColumn {
//                items(movies.value) { movie ->
//                    Text(text = movie.title)
//                }
//            }
//
//
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//}

