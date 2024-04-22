package uz.abdulaziz.smartparking.core.model.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("car_number")
    val carNumber: String, // 01 A777BA
    @SerializedName("date_of_birth")
    val dateOfBirth: String, // 2004-01-12
    @SerializedName("email")
    val email: String, // salikhdev1@gmail.com
    @SerializedName("fullname")
    val fullname: String, // Muhammadsolih Abdugafforov
    @SerializedName("image")
    val image: Any // null
)