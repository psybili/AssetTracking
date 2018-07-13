package sample.onursaygili.assettracking.util

import sample.onursaygili.assettracking.data.local.Trip
import java.util.*
import kotlin.collections.ArrayList

class TripTestUtil {

    companion object {
        fun getTrip() = Trip(getRandomLong())

        fun getTripList(): List<Trip> {
            return getTripList(10)
        }

        fun getTripList(size: Int): List<Trip> {
            val tripList = ArrayList<Trip>(10)
            for (i in 0..size)
                tripList.add(getTrip())
            return tripList
        }

        private fun getRandomLong() = Random().nextLong()

        inline fun <reified T> toArray(list: List<*>): Array<T> {
            return (list as List<T>).toTypedArray()
        }
    }


}