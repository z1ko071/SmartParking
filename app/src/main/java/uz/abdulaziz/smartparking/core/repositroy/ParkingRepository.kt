package uz.abdulaziz.smartparking.core.repositroy

import kotlinx.coroutines.Dispatchers
import uz.abdulaziz.smartparking.core.model.MessageResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponseItem
import uz.abdulaziz.smartparking.core.network.ApiClient
import uz.salikhdev.ecology.core.util.ResultWrapper
import uz.salikhdev.ecology.core.util.parseResponse

class ParkingRepository {

    private var service = ApiClient.createParkingService()

    suspend fun getParking(): ResultWrapper<ParkingResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getParking()
        }
    }

    suspend fun getNearParking(lat: Double, lon: Double): ResultWrapper<ParkingResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getNearParking(lat, lon)
        }
    }

    suspend fun enterParking(id: Int, electro: String): ResultWrapper<MessageResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.enterParking(id, electro)
        }
    }

    suspend fun exitParking(id: Int, electro: String): ResultWrapper<MessageResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.exitParking(id, electro)
        }
    }

    suspend fun getDetailParking(id: Int): ResultWrapper<ParkingResponseItem?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getDetailParking(id)
        }
    }

}