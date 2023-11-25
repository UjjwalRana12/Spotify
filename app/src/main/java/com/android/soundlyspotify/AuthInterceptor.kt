package com.android.soundlyspotify
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class AuthInterceptor(private var token: String) : Interceptor {

    fun updateToken(newToken: String) {
        this.token = newToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}
fun createOkHttpClient(authToken: String): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(authToken))
        .build()
}
