package sample.onursaygili.assettracking.data

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sample.onursaygili.assettracking.data.local.*
import sample.onursaygili.assettracking.data.remote.TripService
import javax.inject.Inject

class TripRepository
@Inject constructor(
        private val tripDao: TripDao,
        private val locationDao: LocationDao,
        private val tripService: TripService
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

    fun updateTrips() {
        tripService.getTrips()
                .map {
                    tripDao.saveAll(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Flowable.empty())
                .subscribe()
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

}