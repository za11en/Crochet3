package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.Crochet3Theme
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.crochet3.ui.theme.AppPrime
import androidx.compose.ui.text.input.ImeAction
import com.example.crochet3.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(navController: NavController, initialSearchText: String) {
    val searchText = remember { mutableStateOf(initialSearchText) }
    val patterns = crochetPatterns.filter { it.name.contains(searchText.value, ignoreCase = true) }
    Scaffold(
        topBar = {TopAppBar(navController, "Search") },
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Transparent,
        modifier = Modifier.background(appGradient())
    ) {
            Column (modifier=Modifier.padding(top = 100.dp)){
                Surface(color = Color.White, shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)) {
                    val isFocused = remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = {
                            if (searchText.value.isNotEmpty()) {
                                navController.navigate("searchPage/${searchText.value}")
                            }
                        }, modifier = Modifier.align(Alignment.CenterStart)) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        BasicTextField(
                            value = searchText.value,
                            onValueChange = { searchText.value = it },
                            cursorBrush = SolidColor(Color.Black),
                            textStyle = TextStyle(color = Color.Black, fontSize = 24.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(start = 50.dp, top = 10.dp, bottom = 10.dp, end = 50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .onFocusChanged { focusState ->
                                    isFocused.value = focusState.isFocused
                                },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                if (searchText.value.isNotEmpty()) {
                                    navController.navigate("searchPage/${searchText.value}")
                                }
                            })
                        )
                        if (searchText.value.isEmpty() && !isFocused.value) {
                            Text(
                                text = "Search Patterns",
                                color = Color.Gray,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .padding(start = 50.dp)
                                    .align(Alignment.CenterStart)
                            )
                        }
                        if (!isFocused.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.voice),
                                contentDescription = "voice search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(42.dp)
                                    .padding(end = 10.dp)
                                    .clickable { /*TODO*/ }
                            )
                        }
                        if (searchText.value.isEmpty() && isFocused.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.voice),
                                contentDescription = "voice search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(42.dp)
                                    .padding(end = 10.dp)
                                    .clickable { /*TODO*/ }
                            )
                        }
                        if (searchText.value.isNotEmpty() && isFocused.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.clear),
                                contentDescription = "clear search",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(42.dp)
                                    .padding(end = 10.dp)
                                    .clickable { searchText.value = "" }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                WhiteCard {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 6.dp, end = 6.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Search Results For: ${searchText.value}",
                                lineHeight = 16.sp,
                                color = AppPrime,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 0.dp, bottom = 20.dp, end = 0.dp)
                                    .weight(.7f)
                            )
                            FilterMenu()
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.sort),
                                contentDescription = "sort",
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(36.dp)
                                    .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp)
                                    .clickable { /*TODO*/ }
                            )
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), modifier = Modifier.padding(bottom = 80.dp)
                        ) {
                            items(patterns) { pattern ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .height(175.dp)
                                        .width(175.dp)
                                        .border(6.dp, Color(0xFFFFFFFF), RoundedCornerShape(16.dp))
                                        .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                        .clickable { navController.navigate("patternPage/${pattern.name}") },
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 10.dp,
                                                    topEnd = 10.dp,
                                                    bottomEnd = 0.dp,
                                                    bottomStart = 0.dp
                                                )
                                            )
                                    ) {
                                        Image(
                                            painter = painterResource(id = pattern.imageResId),
                                            contentDescription = "Image for ${pattern.name}",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(135.dp)
                                        )
                                        Text(
                                            text = pattern.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .padding(top = 8.dp, bottom = 8.dp),
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






@Composable
fun FilterMenu() {
    var expanded by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val items = listOf("Easy", "Intermediate", "Advanced", "Expert")
    val checkedItems = remember { mutableStateMapOf<String, Boolean>() }
    items.forEach { label ->
        checkedItems[label] = false
    }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Icon(
            painter = painterResource(id = R.drawable.filter_alt),
            contentDescription = "filter",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(36.dp)
                .padding(top = 0.dp, start = 0.dp, bottom = 0.dp, end = 0.dp)
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .width(screenWidth / 2)
        ) {
            Text(
                text = "Filter Search Results:",
                style = Typography.bodyMedium,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            )
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            Text(
                text = "Difficulty",
                style = Typography.bodyMedium,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            items.forEach { label ->
                Row(
                    Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .clickable { checkedItems[label] = checkedItems[label]?.not() ?: false },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedItems[label] ?: false,
                        onCheckedChange = { checkedItems[label] = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        style = Typography.bodyMedium,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            Text(
                text = "Hook Size",
                style = Typography.bodyMedium,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Slider(
                value = 0.5f,
                onValueChange = { /*TODO*/ },
                valueRange = 0f..1f,
                steps = 16,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Apply",
                style = Typography.bodyMedium,
                color = AppPrime,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }
    }

@Preview(showBackground = true)
@Composable
fun PreviewSearchPage() {
    Crochet3Theme {
        SearchPage(navController = rememberNavController(), initialSearchText = "")
    }
}

