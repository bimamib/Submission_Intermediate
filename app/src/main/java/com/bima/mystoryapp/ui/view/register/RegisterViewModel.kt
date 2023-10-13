package com.bima.mystoryapp.ui.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.mystoryapp.data.repository.StoryRepository
import com.bima.mystoryapp.data.response.RegisterResponse
import kotlinx.coroutines.launch
import com.bima.mystoryapp.data.Result

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>()
    val registrationResult: LiveData<Result<RegisterResponse>> get() = _registrationResult

    fun register(name: String, email: String, password: String) {
        _registrationResult.value = Result.Loading

        viewModelScope.launch {
            val result = storyRepository.register(name, email, password)
            _registrationResult.value = result
        }
    }
}