package com.bima.mystoryapp.data.repository

import com.bima.mystoryapp.data.remote.retrofit.ApiService
import com.bima.mystoryapp.data.response.RegisterResponse
import com.bima.mystoryapp.data.Result
import com.bima.mystoryapp.data.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class StoryRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse> {
        Result.Loading
        return try {
            // Lakukan pemanggilan API untuk registrasi
            val response = apiService.register(name, email, password)

            if (response.error == true) {
                Result.Error(response.message ?: "Unknown error")
            } else {
                Result.Success(response)
            }
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error("Network error: ${e.message}")
        }
    }
}