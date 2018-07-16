package sample.onursaygili.assettracking.data.remote

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import sample.onursaygili.assettracking.data.local.Trip

interface TripService {

    @FormUrlEncoded
    @POST("/save")
    fun save(@Body trip: Trip)

    @GET("/trips")
    fun getTrips(): Flowable<List<Trip>>
}
