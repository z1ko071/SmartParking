package uz.abdulaziz.smartparking.core.network.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.abdulaziz.smartparking.core.model.MessageResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponseItem

interface ParkingService {

    @GET("/api/parkings/")
    suspend fun getParking(): Response<ParkingResponse?>

    @GET("/api/near_parking/location")
    suspend fun getNearParking(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<ParkingResponse?>

    @POST("/api/user_arrived/{car_id}/car")
    suspend fun enterParking(
        @Path("car_id") id: Int,
        @Query("electro") electro: String
    ): Response<MessageResponse?>

    @POST("/api/user_went/{car_id}/car")
    suspend fun exitParking(
        @Path("car_id") id: Int,
        @Query("electro") electro: String
    ): Response<MessageResponse?>

    @GET("/api/parking/{id}/")
    suspend fun getDetailParking(@Path("id") id: Int): Response<ParkingResponseItem?>

}