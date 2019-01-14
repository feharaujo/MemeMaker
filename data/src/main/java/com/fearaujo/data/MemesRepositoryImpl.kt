package com.fearaujo.data

import androidx.lifecycle.LiveData
import com.fearaujo.data.remote.RemoteRepository
import com.fearaujo.model.ResultModel

class MemesRepositoryImpl : MemesRepository {

    private val remoteRepository: RemoteRepository by lazy {
        RemoteRepository()
    }

    override fun fetchMemes(page: Int): LiveData<ResultModel> {
        return remoteRepository.fetchMemes(page)
    }
}