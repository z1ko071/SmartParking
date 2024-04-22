package uz.abdulaziz.smartparking.core.repositroy

import kotlinx.coroutines.Dispatchers
import uz.abdulaziz.smartparking.core.model.road.DirectionsResponse
import uz.abdulaziz.smartparking.core.network.ApiClient
import uz.salikhdev.ecology.core.util.ResultWrapper
import uz.salikhdev.ecology.core.util.parseResponse

class DirectionsRepository {

    private var service = ApiClient.directionsApiService()

    suspend fun getMapData(from: String, to: String): ResultWrapper<DirectionsResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getDirections(from, to, "AIzaSyC1d9JunK5z05cQdVBnAAQktV2E1xszY4M")
        }
    }


}