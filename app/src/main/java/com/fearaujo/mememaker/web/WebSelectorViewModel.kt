package com.fearaujo.mememaker.web

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fearaujo.data.MemesRepository
import com.fearaujo.data.MemesRepositoryImpl
import com.fearaujo.model.MemeModel

class WebSelectorViewModel : ViewModel() {

    val memesData = MutableLiveData<List<MemeModel>>()

    val loadingState = ObservableBoolean()
    val errorState = ObservableBoolean()

    private var paginationNumber: Int = 1

    private val repository: MemesRepository by lazy {
        MemesRepositoryImpl()
    }

    fun fetchMemes() {
        if (memesData.value == null) {
            //memesData.value = getLoadingModel()
            loadingState.set(true)
            errorState.set(false)

            observeMemesRequest()
        }

    }

    private fun observeMemesRequest() {
        repository.fetchMemes(paginationNumber).observeForever {
            memesData.postValue(it?.data)
            loadingState.set(false)
            //errorState.set(it.error)
        }
    }

    fun requestNextPage() {
        paginationNumber++
        observeMemesRequest()
    }
}
