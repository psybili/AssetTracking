package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import sample.onursaygili.assettracking.data.local.*
import javax.inject.Inject

class TripRepository
@Inject constructor(
        private val tripDao: TripDao,
        private val locationDao: LocationDao
) {
////////////////////////////////////////////////////////////////////////////////////////////////////
    fun getTrips(): LiveData<List<Trip>> {
        return tripDao.listFinishedTrips()
    }

    fun getCurrentTrip(): LiveData<Trip> {
        return tripDao.getCurrentTrip()
    }

    fun createTrip(trip: Trip) {
        tripDao.save(trip)
    }

    fun getCurrentTripAndAllLocations(): TripAndAllLocations {
        return tripDao.getCurrentTripLocations()
    }

    fun saveTrip(trip: Trip) {
        createTrip(trip)
    }

    fun saveLocation(location: Location) {
        locationDao.save(location)
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

}