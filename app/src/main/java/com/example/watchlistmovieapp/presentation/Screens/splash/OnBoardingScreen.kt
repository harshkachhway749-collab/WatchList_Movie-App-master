package com.example.watchlistmovieapp.presentation.Screens.splash

import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchlistmovieapp.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onStartApp: () -> Unit // Callback to navigate to main app
) {
    val pagerState = rememberPagerState(pageCount = { 2 }) // 2 pages
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> FirstOnboardingScreen()
            1 -> SecondOnboardingScreen(
                onSwipeRight = {
                    coroutineScope.launch {
                        // Start the app when swiped right from page 1
                        onStartApp()
                    }
                }
            )
        }
    }

    // Optional: Dots indicator to show page progress

}

@Composable
fun FirstOnboardingScreen() {
    val pagerState = rememberPagerState(pageCount = { 2 }) // 2 pages
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)), // Dark background for cinematic feel
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Logo (replace with your actual logo)
        Image(
            painter = painterResource(
                id =R.drawable.img_1
            ), // Replace with your logo URL or resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // App Name
        Text(
            text = "Watchlist Movies",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hint to swipe left
        Text(
            text = "Swipe left to continue",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            ,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val color = if (pagerState.currentPage == index) Color.Red else Color.White
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}


@Composable
fun SecondOnboardingScreen(
    onSwipeRight: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Logo
        Image(
            painter = painterResource(
                id =R.drawable.img_1
            ), // Replace with your logo URL or resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Swipe Button
        IconButton(
            onClick = {
                onSwipeRight()
            },
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null,
                tint = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instruction Text
        Text(
            text = "Click to start app",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}



//@Composable
//fun MovieApp(
//    viewModel: MyViewModel
//) {
//    var isOnboardingComplete by remember { mutableStateOf(false) }
//
//    MaterialTheme(
//        colorScheme = darkColorScheme(
//            surface = Color(0xFFD22A2A),
//            onSurface = Color.White,
//            primary = Color(0xFFB71C1C)
//        )
//    ) {
//        if (!isOnboardingComplete) {
//            OnboardingScreen(
//                onStartApp = { isOnboardingComplete = true }
//            )
//        } else {
//            MainScreenUI(viewModel) // Your main app screen with top and bottom bars
//        }
//    }
//}