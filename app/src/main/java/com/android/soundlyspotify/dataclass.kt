package com.android.soundlyspotify

data class EmailRegistrationRequest(val email: String, val username: String)
data class PhoneRegistrationRequest(val username: String, val phone_number: String)
data class VerificationRequest(val username: String, val otp: String)
data class UserLoginRequest(val username: String)
data class ForgotEmailVerificationRequest(val email: String, val otp: String)
data class ApiResponse(val success: Boolean, val message: String)
