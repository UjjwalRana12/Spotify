package com.android.soundlyspotify

data class EmailRegistrationRequest(val username: String, val email: String)
data class PhoneRegistrationRequest(val username: String, val phone_number: String)
data class VerificationRequest(val username: String, val otp: String)
data class UserLoginRequest(val username: String)
data class ForgotEmailVerificationRequest(val email: String)

data class ForgotPhoneNumberRequest(val phoneNumber: String)
data class ApiResponse(val success: Boolean, val message: String,val data: ResponseData?)
data class ApikaResponse<T>(val success: Boolean, val message: String, val data: T?)


data class ResponseData(val access_token: String)


data class Song(
    val id: Int,
    val name: String,
    val date_added: String,
    val genre: String,
    val mood: String,
    val song_url: String?,
    val thumbnail_url: String?,
    val artist: String,
    val language: String?
)
