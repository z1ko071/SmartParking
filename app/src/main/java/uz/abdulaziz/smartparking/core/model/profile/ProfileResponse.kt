package uz.abdulaziz.smartparking.core.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("car_number")
    val carNumber: String, // null
    @SerializedName("date_of_birth")
    val dateOfBirth: Any, // null
    @SerializedName("email")
    val email: String, // solih@gmail.com
    @SerializedName("full_name")
    val fullName: String, // null
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("image")
    val image: Any // null
)