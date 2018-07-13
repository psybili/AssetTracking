package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class TripAndAllLocations {

    @Embedded
    var trip: Trip? = null

    @Relation(
            parentColumn = "id",
            entityColumn = "tripId",
            entity = Location::class)
    var locations: List<Location> = ArrayList()
}