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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.watchlistmovieapp.data.Entity.WatchMovie
import com.example.watchlistmovieapp.data.Models.PopularMovies.Result
import com.example.watchlistmovieapp.domain.MyViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreenUI(
    viewModel: MyViewModel
) {
    val popularMovies = viewModel.popularMovies.value
    val trendingMovies = viewModel.trendingMovies.value
    val isSelectedPopular = remember { mutableStateOf(false) }
    val isSelectedTrending = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x86402F27)) // Dark background for modern look
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Popular Movies",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                OutlinedButton(
                    onClick = {
                        isSelectedPopular.value = !isSelectedPopular.value
                        isSelectedTrending.value = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelectedPopular.value) {
                            Color(0xFF696212)
                        } else {
                            Color(0xFF563E32)
                        },
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFF1E1D21))
                ) {
                    Text(
                        text = if (isSelectedPopular.value) "Collapse" else "See All",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            LazyRow(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                items(
                    items = popularMovies?.results ?: emptyList(),
                ) { popularMovie ->
                    if (isSelectedPopular.value) {
                        PopularMovieCard2(
                            popularMovie = popularMovie,
                            viewModel = viewModel
                        )
                    } else {
                        PopularMovieCard(
                            popularMovie = popularMovie,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Trending Movies",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                OutlinedButton(
                    onClick = {
                        isSelectedTrending.value = !isSelectedTrending.value
                        isSelectedPopular.value = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelectedTrending.value) {
                            Color(0xFF190734)
                        } else {
                            Color(0xFF6D4535)
                        },
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFF2B282E))
                ) {
                    Text(
                        text = if (isSelectedTrending.value) "Collapse" else "See All",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            LazyRow(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                items(
                    items = trendingMovies?.results ?: emptyList(),
                ) { trendingMovie ->
                    if (isSelectedTrending.value) {
                        TrendingMovieCard2(
                            TrendingMovie = trendingMovie,
                            viewModel = viewModel
                        )
                    } else {
                        TrendingMovieCard(
                            TrendingMovie = trendingMovie,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopularMovieCard(
    popularMovie: Result,
    viewModel: MyViewModel,
) {
    val isInWatchlist = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(popularMovie.id) {
        isInWatchlist.value = viewModel.isMovieInWatchlist(popularMovie.id)
    }
    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(160.dp)
            .height(360.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(0xFF1E1E1E),
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
                model = "https://image.tmdb.org/t/p/w500/${popularMovie.poster_path}",
                contentDescription = popularMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = popularMovie.title,
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
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = String.format("%.1f", popularMovie.vote_average),
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
                                tmdbId = popularMovie.id,
                                title = popularMovie.title,
                                poster_path = popularMovie.poster_path,
                                description = popularMovie.overview,
                                rating = popularMovie.vote_average,
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
                        Color(0xFF223831)
                    } else {
                        Color(0xFF6D5252)
                    },
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF100F11))
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

@Composable
fun TrendingMovieCard(
    TrendingMovie: com.example.watchlistmovieapp.data.Models.trendingMoviesModel.Result,
    viewModel: MyViewModel
) {
    val isInWatchlist = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(TrendingMovie.id) {
        isInWatchlist.value = viewModel.isMovieInWatchlist(TrendingMovie.id)
    }
    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(160.dp)
            .height(360.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(0xFF1E1E1E),
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
                model = "https://image.tmdb.org/t/p/w500/${TrendingMovie.poster_path}",
                contentDescription = TrendingMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = TrendingMovie.title,
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
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = String.format("%.1f", TrendingMovie.vote_average),
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
                                tmdbId = TrendingMovie.id,
                                title = TrendingMovie.title,
                                poster_path = TrendingMovie.poster_path,
                                description = TrendingMovie.overview,
                                rating = TrendingMovie.vote_average,
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
                        Color(0xFF223831)
                    } else {
                        Color(0xFF4B4040)
                    },
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF383041))
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

@Composable
fun PopularMovieCard2(
    popularMovie: Result,
    viewModel: MyViewModel
) {
    val isInWatchlist = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(popularMovie.id) {
        isInWatchlist.value = viewModel.isMovieInWatchlist(popularMovie.id)
    }

    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(320.dp)
            .height(600.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(0xFF1E1E1E),
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
                model = "https://image.tmdb.org/t/p/w500/${popularMovie.poster_path}",
                contentDescription = popularMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = popularMovie.title,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
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
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = String.format("%.1f", popularMovie.vote_average),
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
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
                    text = "Adult: ",
                    fontSize = 14.sp,
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = if (popularMovie.adult) "Yes" else "No",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Overview: ",
                    fontSize = 14.sp,
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = popularMovie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        if (!isInWatchlist.value) {
                            val movie = WatchMovie(
                                id = 0,
                                tmdbId = popularMovie.id,
                                title = popularMovie.title,
                                poster_path = popularMovie.poster_path,
                                description = popularMovie.overview,
                                rating = popularMovie.vote_average,
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
                        Color(0xFF223831)
                    } else {
                        Color(0xFF424242)
                    },
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF27242A))
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

@Composable
fun TrendingMovieCard2(
    TrendingMovie: com.example.watchlistmovieapp.data.Models.trendingMoviesModel.Result,
    viewModel: MyViewModel
) {
    val isInWatchlist = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(TrendingMovie.id) {
        isInWatchlist.value = viewModel.isMovieInWatchlist(TrendingMovie.id)
    }

    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(320.dp)
            .height(600.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(0xFF1E1E1E),
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
                model = "https://image.tmdb.org/t/p/w500/${TrendingMovie.poster_path}",
                contentDescription = TrendingMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = TrendingMovie.title,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
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
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = String.format("%.1f", TrendingMovie.vote_average),
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
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
                    text = "Adult: ",
                    fontSize = 14.sp,
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = if (TrendingMovie.adult) "Yes" else "No",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Overview: ",
                    fontSize = 14.sp,
                    color = Color(0xFFBB86FC)
                )
                Text(
                    text = TrendingMovie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            HorizontalDivider(
                color = Color(0xFF424242),
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp
            )
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        if (!isInWatchlist.value) {
                            val movie = WatchMovie(
                                id = 0,
                                tmdbId = TrendingMovie.id,
                                title = TrendingMovie.title,
                                poster_path = TrendingMovie.poster_path,
                                description = TrendingMovie.overview,
                                rating = TrendingMovie.vote_average,
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
                        Color(0xFF223831)
                    } else {
                        Color(0xFF424242)
                    },
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF18161A))
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