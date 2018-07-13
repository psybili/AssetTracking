package sample.onursaygili.assettracking.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface TripDao : BaseDao<Trip> {

    @Query("SELECT * FROM Trip WHERE tripStatus = 0")
    fun getTripList(): LiveData<List<Trip>>

    @Query("SELECT * FROM Trip WHERE id = :id")
    fun getTripById(id: Long): LiveData<Trip>

    @Query("SELECT * FROM Trip WHERE tripStatus = 1 LIMIT 1")
    fun getCurrentTrip(): LiveData<Trip>

}