package com.example.newsappcompose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.newsappcompose.navigation.NewsScreenNavigation
import com.example.newsappcompose.viewmodel.NewsViewModel
import com.example.newsappcompose.widgets.MyEventListener
import com.example.newsappcompose.widgets.NewsItem
import com.example.servicedata.model.ArticlesItem
import com.example.servicedata.model.DetailNewsItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: NewsViewModel){
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Gray,
            elevation = 5.dp) {
            Text(text = "24/7 News",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground, fontSize = 28.sp)
        }
    }) {
        MainContent(navController = navController,viewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
private fun TestTopBar() {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.White,
            elevation = 5.dp) {
            Text(text = "24/7 News",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground, fontSize = 28.sp)
        }
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)) {
            Text(text = "Top Headline USA",fontSize = 24.sp, fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.padding(20.dp))
        }
    }
}

@Composable
fun MainContent(navController: NavController, viewModel: NewsViewModel){

    MyEventListener {
        when (it) {
            Lifecycle.Event.ON_START -> {
                viewModel.getTopHeadlinesUsa()
            }
            else -> {

            }
        }
    }

    val data = viewModel.newsDataUiState.collectAsState()

    val scrollState = rememberScrollState()

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(state = swipeRefreshState, onRefresh = viewModel::refresh) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.LightGray)
        ) {
            Text(
                text = "Top Headline USA",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            NewsItem(news = data.value.data?.articles ?: listOf()) {
                val detailNews = DetailNewsItem(
                    title = it.title,
                    description = it.description.toString(),
                    image = it.urlToImage.toString(),
                    content = it.content.toString(),
                    publishedAt = it.publishedAt
                )
                viewModel.setDetailNews(detailNews)
                navController.navigate(NewsScreenNavigation.DetailScreen.route)
            }
        }
    }
}