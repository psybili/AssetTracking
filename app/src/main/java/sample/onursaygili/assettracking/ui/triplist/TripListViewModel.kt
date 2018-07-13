package sample.onursaygili.assettracking.ui.triplist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.Trip
import javax.inject.Inject

class TripListViewModel @Inject constructor(var repository: TripRepository) : ViewModel() {

    val trips: LiveData<List<Trip>> = repository.getLocalTrips()

    init {
        updateTripList()
    }

    fun updateTripList() {
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
