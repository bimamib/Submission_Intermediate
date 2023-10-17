package com.bima.mystoryapp.data.repository

import androidx.lifecycle.liveData
import com.bima.mystoryapp.data.remote.retrofit.ApiService
import com.bima.mystoryapp.data.response.RegisterResponse
import com.bima.mystoryapp.data.Result
import com.bima.mystoryapp.data.pref.UserModel
import com.bima.mystoryapp.data.pref.UserPreference
import com.bima.mystoryapp.data.remote.retrofit.ApiConfig
import com.bima.mystoryapp.data.response.ErrorResponse
import com.bima.mystoryapp.data.response.LoginResponse
import com.bima.mystoryapp.data.response.UploadResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import okhttp3.MultipartBody

class StoryRepository private constructor(
    private val apiService: ApiService,

    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

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
            Result.Error(errorMessage.toString())
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        Result.Loading
        return try {
            val response = apiService.login(email, password)

            if (response.error == true) {
                Result.Error(response.message)
            } else {
                val session = UserModel(name = response.loginResult.name, email = email, token = response.loginResult.token, isLogin = true)
                saveSession(session)
                ApiConfig.token = response.loginResult?.token ?: ""
                Result.Success(response)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error(errorMessage.toString())
        }
    }

    fun getStories() = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error(errorMessage.toString())
        }
    }

    fun uploadStories(imageFile: File, description: String) = liveData {
        emit(Result.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
            emit(Result.Error("Error: $errorResponse"))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}