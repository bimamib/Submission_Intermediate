package com.bima.mystoryapp.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bima.mystoryapp.data.pref.UserModel
import com.bima.mystoryapp.data.repository.StoryRepository
import com.bima.mystoryapp.data.response.StoryResponse
import kotlinx.coroutines.launch
import com.bima.mystoryapp.data.Result

class MainViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    private val listStory = MutableLiveData<Result<StoryResponse>>()
    val dataStory: LiveData<Result<StoryResponse>> = listStory
    init {
        getStories()
    }
    fun getStories() {
        viewModelScope.launch {
            val storyResponse = repository.getStories()
            storyResponse.asFlow().collect {
                listStory.value = it
            }
        }
    }
}