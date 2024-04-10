package com.example.crochet3

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.Poppins
import com.example.crochet3.ui.theme.Typography
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatternPage(navController: NavController, patternName: String, sharedPrefManager: SharedPreferencesRepository) {
    val scope = rememberCoroutineScope()
    val selectedImage = remember { mutableStateOf<Int?>(null) }
    val openLinkLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        }
    val isFavorite = remember { mutableStateOf(sharedPrefManager.isFavorite(patternName)) }
    val onFavoriteClick: (Boolean) -> Unit = { isFavorite ->
        if (isFavorite) {
            sharedPrefManager.addFavorite(patternName)
        } else {
            sharedPrefManager.removeFavorite(patternName)
        }
    }
    val pattern = crochetPatterns.first { it.name == patternName }
    val patternImages = listOf(pattern.imageResId, pattern.image2, pattern.image3)
    val pagerState = rememberPagerState(pageCount = { 3 })

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appGradient())
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 14.dp, bottom = 16.dp, end = 14.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .background(Color(0x20990040), RoundedCornerShape(12.dp))
                    )
                    {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { navController.navigateUp() })
                    }
                    Text(
                        text = pattern.name,
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FloatingActionButton(
                            onClick = { /* Handle share action here */ },
                            modifier = Modifier
                                .size(42.dp)
                                .shadow(6.dp, CircleShape),
                            containerColor = Color.White
                        )
                        {
                            Icon(
                                Icons.Filled.Share,
                                tint = Color.Black,
                                contentDescription = "Share",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        FloatingActionButton(
                            onClick = {
                                isFavorite.value = !isFavorite.value
                                onFavoriteClick(isFavorite.value)
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .shadow(6.dp, CircleShape),
                            containerColor = Color.White
                        )
                        {
                            Icon(
                                imageVector = if (isFavorite.value as Boolean) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isFavorite.value as Boolean) "Filled Favorite Icon" else "Empty Favorite Icon",
                                tint = if (isFavorite.value as Boolean) Color.Red else Color.Gray,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
                LazyRow {
                    items(patternImages) { imageResId ->
                        Box(
                            modifier = Modifier
                                .size(210.dp) // Set the size of the LazyRow item
                                .padding(start = 16.dp, top = 8.dp, bottom = 16.dp, end = 0.dp)
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .border(4.dp, Color.White, RoundedCornerShape(16.dp))
                                .clickable { selectedImage.value = imageResId }
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = "Pattern Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize() // Make the image fill the maximum size
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF), RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp))
                        .padding(top = 8.dp, start = 0.dp, end = 0.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                color = AppPrimeSecond,
                            )
                        },
                        divider = { HorizontalDivider(thickness = 1.dp, color = Color.Gray) },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    ) {
                        Tab(
                            text = { Text("Info", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                            selected = pagerState.currentPage == 0,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                        )
                        Tab(
                            text = { Text("Materials", fontSize = 18.sp) },
                            selected = pagerState.currentPage == 1,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                        )
                        Tab(
                            text = { Text("Instructions", fontSize = 18.sp) },
                            selected = pagerState.currentPage == 2,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(2)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {
                            0 -> {
                                Column(
                                    horizontalAlignment = Alignment.Start, modifier = Modifier
                                        .padding(start = 16.dp, end = 16.dp)
                                ) {
                                    val difficultyColor = when (pattern.difficulty) {
                                        Difficulty.BEGINNER -> Color.Green
                                        Difficulty.EASY -> Color.Green
                                        Difficulty.INTERMEDIATE -> Color.Blue
                                        Difficulty.HARD -> Color.Red
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Difficulty: ",
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                        Text(
                                            text = "${pattern.difficulty}",
                                            color = difficultyColor,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC))
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Designed By: ",
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                        Text(
                                            text = pattern.creatorname,
                                            color = Color.Blue,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                            modifier = Modifier
                                                .clickable {
                                                    val openURL = Intent(Intent.ACTION_VIEW)
                                                    openURL.data = Uri.parse(pattern.creatorlink)
                                                    openLinkLauncher.launch(
                                                        Intent.createChooser(
                                                            openURL,
                                                            "Open ${pattern.creatorname}'s website"
                                                        )
                                                    )
                                                }
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            tint = Color.Blue,
                                            painter = painterResource(id = R.drawable.link),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                                .clickable {
                                                    val openURL = Intent(Intent.ACTION_VIEW)
                                                    openURL.data = Uri.parse(pattern.creatorlink)
                                                    openLinkLauncher.launch(
                                                        Intent.createChooser(
                                                            openURL,
                                                            "Open ${pattern.creatorname}'s website"
                                                        )
                                                    )
                                                }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC))
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Hook Size: ",
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                        Text(
                                            text = pattern.hookSize.size,
                                            color = Color.DarkGray,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC))
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.query_builder),
                                            contentDescription = "Time to complete",
                                            tint = AppPrimeThird,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Time to complete: ",
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left,
                                        )
                                        Text(
                                            text = pattern.timeToComplete,
                                            color = Color.DarkGray,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Left
                                        )

                                    }

                                }
                                Text(
                                    text = pattern.notes,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Left
                                )
                            }

                            1 -> {
                                Column(
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Materials needed for this project",
                                        color = AppPrime,
                                        fontFamily = Poppins,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Left,
                                        style = TextStyle(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    AppPrime,
                                                    Color(0xFFFF29A9)
                                                )
                                            )
                                        )
                                    )
                                    Text(text = pattern.materials)
                                }
                            }

                            2 -> {
                                LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                                    items(8) { step ->
                                        Card(
                                            modifier = Modifier
                                                .background(Color.Transparent)
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .shadow(4.dp, RoundedCornerShape(8.dp))
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .background(Color.White, RoundedCornerShape(8.dp))
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "Step # ${step + 1}",
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp
                                                )
                                                Icon(
                                                    painter = painterResource(id = R.drawable.check_box),
                                                    contentDescription = "Completed Step",
                                                    tint = Color.Green,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        selectedImage.value?.let { imageResId ->
            Dialog(onDismissRequest = { selectedImage.value = null }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .clickable { selectedImage.value = null },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Pattern Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(380.dp)
                    )
                }
            }
        }
    }
}



