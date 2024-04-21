package com.example.crochet3

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crochet3.ui.theme.AppPrime
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crochet3.viewModels.ToolsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Tools(navController: NavController, toolsViewModel: ToolsViewModel = viewModel()) {
    Scaffold(
        topBar = {TopAppBar(navController, "Tools")},
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Transparent,
        modifier = Modifier.background(appGradient())
    ) {
        Column(modifier = Modifier.padding(top = 100.dp)) {
            WhiteCard {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.
                    padding(start = 10.dp, top = 20.dp, end = 10.dp)
                ){
                    items(toolsViewModel.toolNames) { toolName ->
                        ToolCard(toolName) {
                            toolsViewModel.navigateToTool(navController, toolName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolCard(toolName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(top = 8.dp, start= 16.dp, bottom = 16.dp, end = 16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.calculate),
                contentDescription = "Back",
                tint = AppPrime,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = toolName,
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Back",
                tint = AppPrime,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun ToolsPreview() {
    val navController = rememberNavController()
    Tools(navController)
}

