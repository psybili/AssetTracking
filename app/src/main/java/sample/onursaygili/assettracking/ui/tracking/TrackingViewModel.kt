package sample.onursaygili.assettracking.ui.tracking

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.location.Location
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.data.local.TripAndAllLocations
import sample.onursaygili.assettracking.data.remote.getMapUrl
import sample.onursaygili.assettracking.util.ioThread
import java.util.*
import javax.inject.Inject

class TrackingViewModel
@Inject constructor(private val tripRepository: TripRepository)
    : ViewModel() {

    var currentTrip: LiveData<Trip> = tripRepository.getCurrentTrip()

    fun startTracking() {
        val trip = Trip()
        trip.tripStatus = 1
        ioThread {
            tripRepository.saveLocalTrip(trip)
        }
    }

    fun stopTracking() {
        ioThread {
            val tripAndAllLocations = tripRepository.getCurrentTripAndAllLocations()
            val trip = tripAndAllLocations.trip!!
            trip.endDate = Date()
            trip.tripStatus = 0
            if (tripAndAllLocations.locations != null && tripAndAllLocations.locations.isNotEmpty())
                trip.imageUrl = getMapUrl(128, 128, tripAndAllLocations.locations)
            tripRepository.saveTrip(trip)
        }
    }

    fun onLocationUpdate(location: Location?) {
        if (location != null && currentTrip.value != null) {
            val l = sample.onursaygili.assettracking.data.local.Location(
                    currentTrip.value!!.id!!,
                    location.time,
                    location.latitude,
                    location.longitude
            )
            ioThread { tripRepository.saveLocation(l) }
        }
    }

}