package com.example.a6_dogapp.viewmodel

import com.example.a6_dogapp.model.DogBreed
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {
    private val BASE_URL = "https://raw.githubusercontent.com"
    private val api: DogApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        api = retrofit.create(DogApi::class.java)
    }

    fun getDogs(): Single<List<DogBreed>> {
        return api.getDogs()
    }
}

