package com.example.a6_dogapp.viewmodel

import com.example.a6_dogapp.model.DogBreed
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface DogApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}
