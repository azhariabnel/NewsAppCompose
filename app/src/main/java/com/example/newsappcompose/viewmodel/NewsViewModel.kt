package com.example.newsappcompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicedata.Result
import com.example.servicedata.Utilities
import com.example.servicedata.model.ArticlesItem
import com.example.servicedata.model.DetailNewsItem
import com.example.servicedata.model.TopHeaderResponse
import com.example.servicedata.remote.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NewsDataUiState<T>(
    val data: T? = null,
    val success: Boolean = false,
    val error: Exception? = null
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    private val _newsDataUiState = MutableStateFlow(NewsDataUiState<TopHeaderResponse>())
    val newsDataUiState : StateFlow<NewsDataUiState<TopHeaderResponse>> = _newsDataUiState.asStateFlow()

    var detailNewsItem by mutableStateOf<DetailNewsItem?>(null)
        private set

    fun getTopHeadlinesUsa(){
        viewModelScope.launch {
            val result = repository.getTopHeadlines(country = "us", apiKey = Utilities.API_KEY)
            _newsDataUiState.update {
                when(result){
                    is Result.Success -> it.copy(
                        data = result.data, success = true, error = null)
                    is Result.Error -> it.copy(error = result.exception, success = false)
                    }
                }
            }
        }

    fun setDetailNews(newDetailNews : DetailNewsItem){
        detailNewsItem = newDetailNews
    }

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getTopHeadlinesUsa()
            delay(2000)
            _isRefreshing.emit(false)
        }
    }
}


