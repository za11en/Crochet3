package com.example.crochet3

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.crochet3.ui.theme.AppPrime
import com.example.crochet3.ui.theme.AppPrimeSecond
import com.example.crochet3.ui.theme.AppPrimeThird
import com.example.crochet3.ui.theme.Typography
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.Data.CrochetPattern
import com.example.crochet3.Data.Difficulty
import com.example.crochet3.Data.crochetPatterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatternPage(navController: NavController, patternName: String) {
    val scope = rememberCoroutineScope()
    val selectedImage = remember { mutableStateOf<Int?>(null) }
    val openLinkLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result -> }
    val shareLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result -> }
    val pattern = crochetPatterns.first { it.name == patternName }
    val patternImages = listOf(pattern.imageResId, pattern.image2, pattern.image3)
    val pagerState = rememberPagerState(pageCount = { 3 })
    val screenWidth = getScreenWidth()
    Scaffold(
        topBar = { TopAppBarWithShare(navController, pattern, shareLauncher) },
        bottomBar = { BottomBar(navController) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appGradient())
        ) {
            Column {
                ImageCarousel(images = patternImages, onClick = { imageResId -> selectedImage.value = imageResId })
                WhiteCard{
                    PatternTabRow(pagerState, scope)
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {
                            0 -> {PagerPageZero(pattern, openLinkLauncher)}
                            1 -> {PagerPageOne(pattern)}
                            2 -> {PagerPageTwo()}
                            }
                        }
                    }
                    // pager end
                }
            }
        }
        selectedImage.value?.let { imageResId ->
            val scale = remember { mutableStateOf(1f) }
            val rotation = remember { mutableStateOf(0f) }
            val offset = remember { mutableStateOf(Offset.Zero) }
            val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
                scale.value *= zoomChange
                rotation.value += rotationChange
                offset.value += offsetChange
            }
            Dialog(onDismissRequest = { selectedImage.value = null }) {
                Box(
                    modifier = Modifier
                        .clickable { selectedImage.value = null }
                        .fillMaxSize()
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Pattern Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(380.dp)
                            .graphicsLayer(
                                scaleX = scale.value,
                                scaleY = scale.value,
                            )
                            .transformable(state = state)
                            .clickable { selectedImage.value = null }
                    )
                }
            }
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(images: List<Int>, onClick: (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    val scope = rememberCoroutineScope()
    val indicatorVisible = remember { mutableStateOf(true) }

    LaunchedEffect(pagerState.currentPage) {
        indicatorVisible.value = true
        delay(2000)
        indicatorVisible.value = false
    }
    Box {
        HorizontalPager(state = pagerState) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { onClick(images[page]) }
            )
        }
        AnimatedVisibility(
            visible = indicatorVisible.value,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()){
                PagerNumberIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 16.dp, end = 24.dp, bottom = 16.dp)
                )
            }
        }
        CustomPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 12.dp,bottom = 24.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerNumberIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0x55000000), RoundedCornerShape(16.dp))
            .padding(top = 8.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}",
            color = Color.White,
            style = Typography.bodyLarge,
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = AppPrime,
    inactiveColor: Color = AppPrimeSecond,
    circleRadius: Float = 20f,
    spacing: Float = 32f
) {
    val transition = updateTransition(pagerState.currentPage, label = "Page indicator transition")
    val offset by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) },
        label = ""
    ) { page -> page * (circleRadius * 2 + spacing) }
    val animatedPage by animateIntAsState(
        targetValue = pagerState.currentPage,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
    )
    Canvas(modifier = modifier) {
        val circleY = size.height
        val totalWidth = pagerState.pageCount * (circleRadius * 2 + spacing) - spacing
        val startX = (size.width - totalWidth) / 2
        for (i in 0 until pagerState.pageCount) {
            val color = if (i == animatedPage) activeColor else inactiveColor
            drawCircle(color, circleRadius, Offset(startX + (i * (circleRadius * 2 + spacing)), circleY))
        }
        drawCircle(activeColor, circleRadius, Offset(startX + offset, circleY))
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternTabRow(pagerState: PagerState, scope: CoroutineScope) {
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
    ) {
        Tab(
            text = { Text("Info", fontSize = 17.sp, fontWeight = FontWeight.Bold) },
            selected = pagerState.currentPage == 0,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }
        )
        Tab(
            text = { Text("Materials", fontSize = 17.sp) },
            selected = pagerState.currentPage == 1,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        )
        Tab(
            text = { Text("Instructions", fontSize = 17.sp) },
            selected = pagerState.currentPage == 2,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(2)
                }
            }
        )
    }
}
@Composable
fun PagerPageZero(pattern: CrochetPattern, openLinkLauncher: ActivityResultLauncher<Intent>) {
    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier
            .padding(start = 16.dp, top = 16.dp,end = 16.dp)
    ) {
        val difficultyColor = when (pattern.difficulty) {
            Difficulty.BASIC -> Color.Green
            Difficulty.EASY -> Color.Cyan
            Difficulty.INTERMEDIATE -> Color.Blue
            Difficulty.COMPLEX -> Color.Red
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = pattern.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Left,
            )
        }
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
                modifier = Modifier
                    .size(24.dp)
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
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC), modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))
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
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC), modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
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
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC), modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
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
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC), modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
    }
    Text(
        text = pattern.notes,
        color = Color.Black,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        textAlign = TextAlign.Left
    )
}
@Composable
fun PagerPageOne(pattern: CrochetPattern) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Materials needed for this project",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
        )
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFCCCCCC), modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
        Text(text = pattern.materials)
    }
}
@Composable
fun PagerPageTwo() {
    val steps = List(4) {remember { mutableStateOf(false) }}
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(steps) { isComplete ->
            Card(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .shadow(4.dp, RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            Color.White,
                            RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Step # ${steps.indexOf(isComplete) + 1}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Icon(
                        painter = painterResource(
                            id = if (isComplete.value) R.drawable.check_box else R.drawable.checkboxempty),
                        contentDescription = "Completed Step",
                        tint = if (isComplete.value) Color.Green else Color.Gray,
                        modifier = Modifier.clickable { isComplete.value = !isComplete.value }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ImageCarouselPreview() {
    ImageCarousel(images = listOf(R.drawable.amigurumi, R.drawable.b, R.drawable.c), onClick = {})
}
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PatternTabRowPreview() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    PatternTabRow(pagerState, scope)
}
@Preview (showBackground = true)
@Composable
fun PagerPageZeroPreview() {
    val openLinkLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result -> }
    val pattern = crochetPatterns.first() // Use the first pattern for preview
    PagerPageZero(pattern, openLinkLauncher)
}
@Preview(showBackground = true)
@Composable
fun PagerPageOnePreview() {
    val pattern = crochetPatterns.first() // Use the first pattern for preview
    PagerPageOne(pattern)
}
@Preview(showBackground = true)
@Composable
fun PagerPageTwoPreview() {
    PagerPageTwo()
}
@Preview(showBackground = true)
@Composable
fun PatternPagePreview() {
    val navController = rememberNavController()
    PatternPage(navController, "Hats")
}