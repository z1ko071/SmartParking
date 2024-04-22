package uz.abdulaziz.smartparking.core.model.parking


import com.google.gson.annotations.SerializedName

data class ParkingResponseItem(
    @SerializedName("electro_count")
    val electroCount: Int, // 5
    @SerializedName("empty_count")
    val emptyCount: Int, // 15
    @SerializedName("empty_electro_count")
    val emptyElectroCount: Int, // 5
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("image")
    val image: String, // /parking/media/parking_images/10336115_18514980.jpg
    @SerializedName("lat")
    val lat: String, // 41.311081
    @SerializedName("lon")
    val lon: String, // 69.240562
    @SerializedName("max_count")
    val maxCount: Int, // 20
    @SerializedName("phoneNumber")
    val phoneNumber: String, // +998909405577
    @SerializedName("rating")
    val rating: Int, // 3
    @SerializedName("title")
    val title: String // Parking 1
)