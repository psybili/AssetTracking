package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import sample.onursaygili.assettracking.data.local.Location
import sample.onursaygili.assettracking.data.local.LocationDao
import javax.inject.Inject

class LocationRepository
@Inject constructor(
        private val locationDao: LocationDao
) {

    fun getLocations(tripId: Long): LiveData<List<Location>> {
        return locationDao.getLocationsByTripId(tripId)
    }

    fun save(location: Location) {
        locationDao.insert(location)
    }

}