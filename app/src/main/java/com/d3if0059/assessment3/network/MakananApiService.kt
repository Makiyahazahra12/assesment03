package com.d3if0059.assessment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.d3if0059.assessment3.model.OpStatus
import com.d3if0059.assessment3.model.Makanan
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://makiyah.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MakananApiService {
    @GET("json.php")
    suspend fun getMakanan(
        @Query("auth") userId: String
    ): List<Makanan>

    @Multipart
    @POST("json.php")
    suspend fun postMakanan(
        @Part("auth") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("json.php")
    suspend fun deleteMakanan(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object MakananApi {
    val service: MakananApiService by lazy {
        retrofit.create(MakananApiService::class.java)
    }
    fun getMakananUrl(image: String): String {
        return "$BASE_URL$image"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }