package sample.onursaygili.assettracking.ui.tracking

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.Trip
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
        val trip = currentTrip.value!!
        trip.endDate = Date()
        trip.tripStatus = 0
        ioThread {
            tripRepository.saveLocalTrip(trip)
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