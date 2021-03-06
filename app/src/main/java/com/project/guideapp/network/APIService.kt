package com.project.guideapp.network

import com.project.guideapp.network.dto.ExhibitsDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://westkruiskadeapi.azurewebsites.net/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface CapstoneApiService {
    @GET("api/artists")
    suspend fun getArtists(): List<ArtistsDTO>

    @GET("api/exhibits")
    suspend fun getExhibits(): List<ExhibitsDTO>

    @GET("api/artists/{id}/exhibits")
    suspend fun getExhibitDetail(@Path("id") exhibitId: String): List<ExhibitsDTO>
}

object APIService {
    val retrofitService: CapstoneApiService by lazy {
        retrofit.create(CapstoneApiService::class.java)
    }
}