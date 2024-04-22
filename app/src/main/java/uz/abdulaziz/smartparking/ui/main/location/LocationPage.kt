package uz.abdulaziz.smartparking.ui.main.location

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.databinding.PageLocationBinding
import uz.abdulaziz.smartparking.ui.bottomsheet.EnterBottomSheet
import uz.abdulaziz.smartparking.ui.main.MainScreenDirections


class LocationPage : BaseFragment(R.layout.page_location), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private val binding by viewBinding(PageLocationBinding::bind)
    private var myMap: GoogleMap? = null
    private lateinit var geocoder: Geocoder
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var userLocaitonMarker: Marker? = null
    private var userLocationAccuracyCircle: Circle? = null
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var localStorage: LocalStorage
    private var bottomSheet: EnterBottomSheet? = null
    private var userLocation: LatLng? = null
    private var parkingLatLon: LatLng? = null
    private var selectedMarker: Marker? = null


    override fun onViewCreated() {

        localStorage = LocalStorage(requireContext())
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMapMy) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        geocoder = Geocoder(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest.interval = 500
        locationRequest.fastestInterval = 500
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        observable()
    }

    private fun observable() {

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.parkingList.observe(viewLifecycleOwner) {

            for (parking in it) {

                val maker = MarkerOptions()
                maker.position(LatLng(parking.lat.toDouble(), parking.lon.toDouble()))
                if (parking.id.toString() == localStorage.parkingId) {
                    maker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parking_active))
                } else if (parking.electroCount == 0) {
                    maker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parkin))
                } else {
                    maker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_electro_parking))
                }
                maker.title(parking.id.toString())
                myMap?.let { a ->
                    if (parking.id.toString() == localStorage.parkingId) {
                        selectedMarker = a.addMarker(maker)
                    } else {
                        a.addMarker(maker)
                    }
                }
            }

        }
        viewModel.parkingDetail.observe(viewLifecycleOwner) {
            bottomSheet?.setData(it)
        }
        viewModel.enterParkingLD.observe(viewLifecycleOwner) {
            bottomSheet!!.dismiss()
            userLocation?.let {
                val userOrg = "${it.latitude},${it.longitude}"
                val parkingOrg = "${parkingLatLon?.latitude},${parkingLatLon?.longitude}"
                viewModel.getMapData(userOrg, parkingOrg)
            }
            selectedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parking_active))
        }
        viewModel.exitParkingLD.observe(viewLifecycleOwner) {
            bottomSheet?.dismiss()
            selectedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parkin))
        }

        var polylineOptions: PolylineOptions
        var polythene: Polyline? = null
        viewModel.routLD.observe(viewLifecycleOwner) {
            val decodedPolyline =
                it.routes.firstOrNull()?.overviewPolyline?.points?.let { it1 -> decodePoly(it1) } // Replace encodedPolyline with the actual polyline data
            polylineOptions = PolylineOptions()
            if (decodedPolyline != null) {
                polylineOptions.addAll(decodedPolyline)
            }
            polylineOptions.width(5f)
            polylineOptions.color(Color.RED)
            polythene = myMap?.addPolyline(polylineOptions)
            myMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
        }

        viewModel.exitParkingLD.observe(viewLifecycleOwner) {
            polythene?.remove()
        }

    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }
        return poly
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {

        viewModel.getParking()
        val mapStyleOptions =
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_map)
        map.setMapStyle(mapStyleOptions)
        myMap = map
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        myMap?.setOnMarkerClickListener(this)
        zoomToUserLocation()
    }

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let {
                userLocation = LatLng(it.latitude, it.longitude)
            }
            myMap?.let {
                setUserLocationMarker(location = locationResult.lastLocation!!)
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        val locationTask = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
            //  myMap?.addMarker(MarkerOptions().position(latLng))
        }
    }

    private fun setUserLocationMarker(location: Location) {
        Log.d("TAGaaaa", "setUserLocationMarker: ")
        val latlon = LatLng(location.latitude, location.longitude)
        if (userLocaitonMarker == null) {
            val maker = MarkerOptions()
                .position(latlon)
                .anchor(0.5f, 0.5f)
                .rotation(location.bearing)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
            userLocaitonMarker = myMap?.addMarker(maker)
            myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlon, 13.0f))
            Log.d("TAGaaaa", "MARKER: ")
        } else {
            userLocaitonMarker?.position = latlon
            userLocaitonMarker?.rotation = location.bearing
            myMap?.animateCamera(CameraUpdateFactory.newLatLng(latlon))
        }

        if (userLocationAccuracyCircle == null) {
            val circle = CircleOptions()
            circle.center(latlon)
            circle.strokeWidth(4.0f)
            circle.strokeColor(Color.parseColor("#4D5DFA"))
            circle.fillColor(Color.parseColor("#364D5DFA"))
            circle.radius(location.accuracy.toDouble() * 100)
            userLocationAccuracyCircle = myMap?.addCircle(circle)
        } else {
            userLocationAccuracyCircle?.center = latlon
            userLocationAccuracyCircle?.radius = location.accuracy.toDouble() * 100
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        selectedMarker = marker
        bottomSheet = EnterBottomSheet(requireContext())
        viewModel.getParkingDetail(marker.title!!.toInt())

        bottomSheet?.onEnterButton = { id, isElectro, latlng ->
            viewModel.enterParking(id, isElectro)
            parkingLatLon = latlng
        }

        bottomSheet?.onExitButton = { id ->
            viewModel.exitParking(id, localStorage.isElectro)
        }
        bottomSheet?.dialogYes = {
            bottomSheet?.dismiss()
            findNavController().navigate(MainScreenDirections.actionMainScreenToPayScreen())
        }

        return true
    }


}