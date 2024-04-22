package uz.abdulaziz.smartparking.core.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String, // salikhdev@gmail.com
    @SerializedName("full_name")
    val fullName: Any, // null
    @SerializedName("id")
    val id: Int // 3
)