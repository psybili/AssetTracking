package sample.onursaygili.assettracking.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface LocationDao : BaseDao<Location> {

    @Query("SELECT * FROM Location WHERE tripId = :tripId")
    fun getLocationsByTripId(tripId: Long): LiveData<List<Location>>

    @Query("SELECT * FROM Location WHERE tripId = :tripId")
    fun getTripLocations(tripId: Long): LiveData<List<Location>>
}