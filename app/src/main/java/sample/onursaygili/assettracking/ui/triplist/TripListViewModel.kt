package sample.onursaygili.assettracking.ui.triplist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.Trip
import javax.inject.Inject

class TripListViewModel @Inject constructor(repository: TripRepository) : ViewModel() {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val trips: LiveData<List<Trip>> = repository.getTrips()
    ////////////////////////////////////////////////////////////////////////////////////////////////
    init {
        updateTrips()
    }

    fun updateTrips() {
//        repository.getRemoteTrips()
//                .map {
//                    repository.addAll(it)
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(Flowable.empty())
//                .subscribe()
    }
}
