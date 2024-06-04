package com.example.newsappcompose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsappcompose.R
import com.example.servicedata.model.ArticlesItem
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.delay

@Composable
fun MyEventListener(OnEvent:(event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(newValue = OnEvent)
    val lifeCycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifeCycleOwner.value){
        val lifecycle = lifeCycleOwner.value.lifecycle
        val observer = LifecycleEventObserver{ source, event ->
            eventHandler.value(event)
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun NewsItem(news: List<ArticlesItem>, onItemClick: (ArticlesItem) -> Unit) {
    var placeholderVisible by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(2000)
        placeholderVisible = false
    }

    news.forEach { item ->
        Card(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(400.dp)
            .clickable {
                onItemClick(item)
            },
            shape = RoundedCornerShape(corner = CornerSize(15.dp))) {
            if (item.urlToImage == null) {
                Image(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp), painter = painterResource(
                    id = R.drawable.ic_article_gray
                ), contentDescription ="News Image",contentScale = ContentScale.FillBounds )
            } else {
                AsyncImage(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                    .placeholder(
                        visible = placeholderVisible,
                        highlight = PlaceholderHighlight.shimmer()
                    ), model = ImageRequest.Builder(
                    LocalContext.current)
                    .data(item.urlToImage)
                    .build(), contentDescription ="News Image",contentScale = ContentScale.FillBounds )
            }
            Text(text = item.title.toString(), modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .placeholder(
                    visible = placeholderVisible,
                    highlight = PlaceholderHighlight.shimmer()
                ), fontSize = 18.sp, style = MaterialTheme.typography.h6, color = Color.White,
                textAlign = TextAlign.Justify)
        }
    }


}

@Preview
@Composable
private fun PreviewNews() {
    Card(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(400.dp)
        .clickable {
        },
        shape = RoundedCornerShape(corner = CornerSize(15.dp))) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = 15.dp, end = 15.dp), painter = painterResource(
            id = R.drawable.ic_article_gray
        ), contentDescription ="News Image",contentScale = ContentScale.FillBounds )
        Text(text = "Test", modifier = Modifier.padding(start = 20.dp, end = 20.dp), fontSize = 18.sp, style = MaterialTheme.typography.h6, color = Color.White,
            textAlign = TextAlign.Justify)
    }
}