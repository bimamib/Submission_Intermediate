package com.bima.mystoryapp.di

import android.content.Context

object Injection { fun provideRepository(context: Context): StoryRepository {
    val pref = UserPreference.getInstance(context.dataStore)
    val user = runBlocking { pref.getUser().first() }
    val apiService = ApiConfig.getApiService(user.token)
    return StoryRepository.getInstance(apiService, pref)
}

}