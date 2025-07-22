package com.example.watchlistmovieapp.presentation.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.watchlistmovieapp.data.Entity.WatchMovie
import com.example.watchlistmovieapp.data.Models.searchMovieModels.Result
import com.example.watchlistmovieapp.domain.MyViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreenUI(
    viewModel: MyViewModel
) {
    val searchMovies = viewModel.searchMovies.value
    val movieName = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x86402F27)) // Match HomeScreenUI dark theme
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = movieName.value,
                onValueChange = { movieName.value = it },
                placeholder = {
                    Text(
                        text = "Search Movies",
                        color = Color(0xFFBB86FC), // Purple accent
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                shape = RoundedCornerShape(12.dp), // Smoother corners
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF872612),
                    unfocusedBorderColor = Color(0xFF7D3030),
                    cursorColor = Color(0xFF03DAC5), // Teal cursor
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF524040),
                    unfocusedContainerColor = Color(0xFF333B2D)
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.searchMovie(movieName.value) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search movies",
                            tint = Color(0xFF2C9F93), // Teal icon
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val chunkedMovies = searchMovies?.results?.chunked(2) ?: emptyList()

        LazyColumn {
            items(chunkedMovies) { rowMovies ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (movie in rowMovies) {
                        SearchMovieCard(
                            movie,viewModel
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SearchMovieCard(
    searchMovie: Result,
    viewModel: MyViewModel
) {
    val isInWatchlist = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchMovie.id) {
        isInWatchlist.value = viewModel.isMovieInWatchlist(searchMovie.id)
    }

    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(160.dp)
            .height(360.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(0xFF1E1E1E), // Match HomeScreenUI card color
            disabledContainerColor = Color(0xFF4A4A4A),
            disabledContentColor = Color(0xFFBBBBBB)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${searchMovie.poster_path}",
                contentDescription = searchMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = searchMovie.title,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rating: ",
                    fontSize = 14.sp,
                    color = Color(0xFFBB86FC) // Purple accent
                )
                Text(
                    text = String.format("%.1f", searchMovie.vote_average),
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        if (!isInWatchlist.value) {
                            val movie = WatchMovie(
                                id = 0,
                                tmdbId = searchMovie.id,
                                title = searchMovie.title,
                                poster_path = searchMovie.poster_path,
                                description = searchMovie.overview,
                                rating = searchMovie.vote_average,
                                isAddedToWatchListDB = true
                            )
                            viewModel.addMovie(movie)
                            isInWatchlist.value = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (!isInWatchlist.value) {
                        Color(0xFF19430C) // Purple when active
                    } else {
                        Color(0xFF424242) // Gray when in watchlist
                    },
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF1C1920)) // Purple border
            ) {
                Text(
                    text = if (isInWatchlist.value) "In Watchlist" else "Add to Watchlist",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}