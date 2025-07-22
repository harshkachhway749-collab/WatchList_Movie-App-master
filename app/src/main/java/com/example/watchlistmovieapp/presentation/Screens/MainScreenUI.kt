package com.example.watchlistmovieapp.presentation.Screens

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.watchlistmovieapp.domain.MyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUI(
   viewModel: MyViewModel

) {
    val navController= rememberNavController()
    val selectedItem = remember { mutableStateOf(BottomNavItem.Home.route) }




    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(50,80, 50,0),
                modifier = Modifier.clip(CircleShape),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // ✅ Align items vertically in the center
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(painter = painterResource(id = com.example.watchlistmovieapp.R.drawable.img), contentDescription = null
                        , modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape))

                        Text(text = "MovieApp",
                            modifier = Modifier.padding(18.dp),
                            fontSize = 24.sp)

                    }



                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0x86DBA790),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile",
                            modifier = Modifier
                                .border(2.dp, Color.White, CircleShape)
                                ,
                            tint = Color.Red
                        )
                    }
                }
            )


        },
        bottomBar = {



            BottomAppBar(
                windowInsets = WindowInsets(10.dp),


                tonalElevation = 10.dp,

                modifier = Modifier

                    .padding(



                        top=0.dp
                    )      .height(70.dp),
                        containerColor = Color(0xF4523A12)

//                    .clip(RoundedCornerShape(topStart = 160.dp, topEnd = 160.dp, bottomEnd = 160.dp, bottomStart = 160.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        ,

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Home Button
                    IconButton(onClick = { selectedItem.value = BottomNavItem.Home.route
                        navController.navigate(BottomNavItem.Home.route)
                        }) {
                        Icon(
                            imageVector = BottomNavItem.Home.icon,
                            contentDescription = BottomNavItem.Home.title,
                            modifier = Modifier.size(50.dp),
                            tint = if (selectedItem.value == BottomNavItem.Home.route) Color.Red else Color.White
                        )
                    }

                    // Search Button
                    IconButton(onClick = { selectedItem.value = BottomNavItem.Search.route
                        navController.navigate(BottomNavItem.Search.route)}) {
                        Icon(
                            imageVector = BottomNavItem.Search.icon,
                            contentDescription = BottomNavItem.Search.title,
                            modifier = Modifier.size(50.dp),
                            tint = if (selectedItem.value == BottomNavItem.Search.route) Color.Red else Color.White
                        )
                    }

                    // Watchlist Button
                    IconButton(onClick = { selectedItem.value = BottomNavItem.Watchlist.route
                    navController.navigate(BottomNavItem.Watchlist.route)}) {
                        Icon(
                            imageVector = BottomNavItem.Watchlist.icon,
                            contentDescription = BottomNavItem.Watchlist.title,
                            modifier = Modifier.size(50.dp),
                            tint = if (selectedItem.value == BottomNavItem.Watchlist.route) Color.Red else Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0x61095418)) // ✅ This applies the background color
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { getEnterTransition() },
                exitTransition = { getExitTransition() },
                popEnterTransition = { getEnterTransition() },
                popExitTransition = { getExitTransition() }
            ) {
                composable(BottomNavItem.Home.route) {
                    HomeScreenUI(viewModel = viewModel)
                }
                composable(BottomNavItem.Search.route) {
                    SearchScreenUI(viewModel = viewModel)
                }
                composable(BottomNavItem.Watchlist.route) {
                    WatchListScreen(viewModel)
                }
            }
        }

    }


}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Watchlist : BottomNavItem("watchlist", Icons.Default.Star, "Watchlist")
}

@OptIn(ExperimentalAnimationApi::class)
fun getEnterTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { it / 4 }, // Subtler slide from the right
        animationSpec = tween(
            durationMillis = 350,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = 350,
            easing = FastOutSlowInEasing
        )
    ) + scaleIn(
        initialScale = 0.8f, // Less extreme scaling
        animationSpec = tween(
            durationMillis = 350,
            easing = FastOutSlowInEasing
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun getExitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -it / 4 }, // Subtler slide to the left
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    ) + scaleOut(
        targetScale = 0.8f, // Consistent with enter scale
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )
}


