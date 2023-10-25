package com.bima.mystoryapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bima.mystoryapp.data.remote.retrofit.ApiService
import com.bima.mystoryapp.data.response.ListStoryItem
import com.bima.mystoryapp.database.StoryDataBase

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator (
    private val dataBase: StoryDataBase,
    private val apiService: ApiService
) : RemoteMediator<Int, ListStoryItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}