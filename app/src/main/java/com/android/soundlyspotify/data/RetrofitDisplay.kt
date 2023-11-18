package com.android.soundlyspotify.data

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDisplay {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzAxODA1MTczLCJpYXQiOjE2OTkyMTMxNzMsImp0aSI6IjYwOTdkYTkxNTJmMDQ1YzY4YmE1MTBjZWQyMDM4MzAxIiwidXNlcl9pZCI6ImFkbWluIn0.zhhXZrQzl4fls2jh26tGQ6KMuKojlFV8r-rE1LEWT_w" // Replace with your actual access token

    // Define the AuthInterceptor to add the "Authorization" header
    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer $ACCESS_TOKEN")
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

    // Define the OkHttpClient instance with a timeout and AuthInterceptor
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    // Define the Retrofit instance with GsonConverterFactory and OkHttpClient
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    // Define the DisplayInterface instance
    val instance: DisplayInterface = retrofit.create(DisplayInterface::class.java)

    // Define the SongApiService instance
    val songApiService: SongApiService = retrofit.create(SongApiService::class.java)
}
