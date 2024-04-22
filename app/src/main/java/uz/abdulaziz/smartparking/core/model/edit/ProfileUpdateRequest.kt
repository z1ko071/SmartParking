package uz.abdulaziz.smartparking.core.model.edit


data class ProfileUpdateRequest(
    val full_name: String,
    val date_of_birth: String,
    val car_number: String,
)