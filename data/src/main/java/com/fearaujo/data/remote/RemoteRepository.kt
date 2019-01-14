package com.fearaujo.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fearaujo.data.MemesRepository
import com.fearaujo.data.URL_BASE
import com.fearaujo.data.getErrorModel
import com.fearaujo.model.ResultModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RemoteRepository : MemesRepository {

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(jacksonConverter)
                .build()

        retrofit.create(ApiService::class.java)
    }

    private val jacksonConverter: JacksonConverterFactory by lazy {
        JacksonConverterFactory.create(
                jacksonObjectMapper().registerModule(KotlinModule())
        )
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    override fun fetchMemes(page: Int): LiveData<ResultModel> {
        val liveData = MutableLiveData<ResultModel>()
        val call = apiService.fetchMemes(page.toString())
        call.enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>?, response: Response<ResultModel>?) {
                if (response?.body() != null) {
                    liveData.value = response.body()
                } else {
                    liveData.value = getErrorModel()
                }
            }

            override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                liveData.value = getErrorModel()
            }
        })

        return liveData
    }
}