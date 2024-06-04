package com.example.newsappcompose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsappcompose.R
import com.example.newsappcompose.viewmodel.NewsViewModel
import com.example.servicedata.Utilities
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController?, viewModel: NewsViewModel) {

    val scrollState = rememberScrollState()

    val detailUiState = viewModel.detailNewsItem
    val date = Utilities.setDateFormatDayddMMyyyy(detailUiState?.publishedAt.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                elevation = 5.dp
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back",
                        modifier = Modifier
                            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                            .clickable {
                                navController?.popBackStack()
                            })
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(text = "News Detail", modifier = Modifier.padding(top = 10.dp), fontSize = 18.sp)

                }
            }
        },
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
            if (detailUiState?.image == "null"){
                Image(
                    painter = painterResource(id = R.drawable.ic_article_gray), contentDescription = "Placeholder", modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                    , contentScale = ContentScale.FillBounds
                )
            } else {
                AsyncImage(
                    model = detailUiState?.image, contentDescription = "News Image", modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp), contentScale = ContentScale.FillBounds
                )
            }

            Text(text = detailUiState?.title.toString(),fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center, color = Color.Gray, modifier = Modifier.padding(start = 20.dp))
            Text(text = detailUiState?.description.toString(),fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify, color = Color.Gray, modifier = Modifier.padding(20.dp))
            Text(text = detailUiState?.content.toString(),fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify, color = Color.Gray, modifier = Modifier.padding(20.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Published At $date",fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify, color = Color.Gray, modifier = Modifier.padding(20.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewDetail() {
    DetailScreen(navController = null, viewModel = hiltViewModel())
}