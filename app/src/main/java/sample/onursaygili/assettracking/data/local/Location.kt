package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey.CASCADE
import android.databinding.BaseObservable

@Entity(
        primaryKeys = ["tripId", "time"],
        foreignKeys = [(android.arch.persistence.room.ForeignKey(
                entity = Trip::class,
                parentColumns = ["id"],
                childColumns = ["tripId"],
                onUpdate = CASCADE,
                onDelete = CASCADE,
                deferred = true
        ))]
)
data class Location(
        val tripId: Long,
        val time: Long,
        val latitude: Double,
        val longitude: Double
) : BaseObservable()