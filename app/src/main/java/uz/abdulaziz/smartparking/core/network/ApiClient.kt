package uz.abdulaziz.smartparking.core.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.abdulaziz.smartparking.core.app.App
import uz.abdulaziz.smartparking.core.network.service.AuthService
import uz.abdulaziz.smartparking.core.network.service.DirectionsApiService
import uz.abdulaziz.smartparking.core.network.service.ParkingService

object ApiClient {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://parking.salikhdev.uz/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

    private fun getRetrofitMap(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

    private fun OkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor())
            .addInterceptor(chuckerInterceptor())
            .build()
    }

    private fun chuckerInterceptor() = ChuckerInterceptor.Builder(App.instance!!)
        .collector(chuckerCollector())
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(true)
        .build()

    private fun chuckerCollector() = ChuckerCollector(
        context = App.instance!!,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    //  service
    fun createAuthService(): AuthService {
        return getRetrofit().create(AuthService::class.java)
    }

    fun createParkingService(): ParkingService {
        return getRetrofit().create(ParkingService::class.java)
    }

    fun directionsApiService(): DirectionsApiService {
        return getRetrofitMap().create(DirectionsApiService::class.java)
    }


}