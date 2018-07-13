package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import sample.onursaygili.assettracking.data.local.Location
import sample.onursaygili.assettracking.data.local.LocationDao
import sample.onursaygili.assettracking.data.local.Trip
import sample.onursaygili.assettracking.data.local.TripDao
import javax.inject.Inject

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

    fun saveLocalTrip(trip: Trip) {
        tripDao.insert(trip)
    }

    fun saveLocation(location: Location) {
        locationDao.insert(location)
    }

}