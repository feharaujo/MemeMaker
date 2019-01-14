package com.fearaujo.data

import androidx.lifecycle.LiveData
import com.fearaujo.model.ResultModel

interface MemesRepository {

    fun fetchMemes(page: Int): LiveData<ResultModel>

}


