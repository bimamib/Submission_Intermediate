package com.bima.mystoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bima.mystoryapp.data.Result
import com.bima.mystoryapp.data.repository.StoryRepository
import com.bima.mystoryapp.data.response.ListStoryItem
import com.bima.mystoryapp.data.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> {
        return repository.getStoriesWithLocation()
    }
}