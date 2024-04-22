package uz.abdulaziz.smartparking.core.model


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String // sala@gmail.com
)