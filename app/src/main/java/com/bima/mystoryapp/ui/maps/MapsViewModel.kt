package com.bima.mystoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bima.mystoryapp.data.Result
import com.bima.mystoryapp.data.repository.StoryRepository
import com.bima.mystoryapp.data.response.ListStoryItem

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> {
        return repository.getStoriesWithLocation()
    }
}