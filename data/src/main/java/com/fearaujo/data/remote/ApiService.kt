package com.fearaujo.data.remote

import com.fearaujo.model.ResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/{page}")
    fun fetchMemes(@Path("page") page: String): Call<ResultModel>

}