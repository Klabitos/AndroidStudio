package com.klabitos.apitest.`interface`

import com.klabitos.apitest.models.DogResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface API_Service {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogResponse>
}