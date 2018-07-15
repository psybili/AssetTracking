package sample.onursaygili.assettracking.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction

@Dao
interface TripDao : BaseDao<Trip> {

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Query("SELECT * FROM Trip WHERE tripStatus = 0")
    fun listFinishedTrips(): LiveData<List<Trip>>

    @Query("SELECT * FROM Trip WHERE tripStatus = 1 LIMIT 1")
    fun getCurrentTrip(): LiveData<Trip>

    @Transaction
    @Query("SELECT * FROM Trip WHERE tripStatus = 1")
    fun getCurrentTripLocations(): TripAndAllLocations

    @Transaction
    @Query("SELECT * FROM Trip WHERE id = :id")
    fun getTripLocations(id: Long): LiveData<TripAndAllLocations>
////////////////////////////////////////////////////////////////////////////////////////////////////

}