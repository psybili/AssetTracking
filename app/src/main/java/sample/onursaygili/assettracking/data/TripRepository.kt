package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import android.util.Log
import sample.onursaygili.assettracking.data.local.*
import javax.inject.Inject
import kotlin.concurrent.timer

class TripRepository
@Inject constructor(
        private val tripDao: TripDao,
        private val locationDao: LocationDao
) {

    fun getLocalTrips(): LiveData<List<Trip>> {
        return tripDao.getTripList()
    }

    fun addAllLocalTrips(tripList: List<Trip>) {
        tripDao.addAll(tripList)
    }

    fun getCurrentTrip(): LiveData<Trip> {
        return tripDao.getCurrentTrip()
    }

    fun saveLocation(location: Location) {
        locationDao.insert(location)
    }


    fun getTripAndAllLocations(tripId: Long): LiveData<TripAndAllLocations> {
        return tripDao.getTripLocations(tripId)
    }

    fun getCurrentTripAndAllLocations(): TripAndAllLocations {
        return tripDao.getCurrentTripLocations()
    }

    fun saveTrip(trip: Trip) {
        saveLocalTrip(trip)
        val tripAndAllLocations = getTripAndAllLocations(trip.id!!)
        val locations = tripAndAllLocations.value?.locations
    }

    fun saveLocalTrip(trip: Trip) {
        tripDao.insert(trip)
    }

}