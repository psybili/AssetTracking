package sample.onursaygili.assettracking.di.module

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sample.onursaygili.assettracking.App
import sample.onursaygili.assettracking.data.local.TripDao
import sample.onursaygili.assettracking.data.local.TripDatabase
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.LocationDao
import sample.onursaygili.assettracking.data.remote.TripService
import javax.inject.Singleton

@Module
class DataModule(app: App) {
    // Room
    private val database: TripDatabase = Room.databaseBuilder(
            app,
            TripDatabase::class.java,
            "Trips.db"
    ).build()

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, locationDao: LocationDao, tripService: TripService) = TripRepository(tripDao, locationDao, tripService)

    @Provides
    @Singleton
    fun provideTripDao(database: TripDatabase) = database.tripDao()

    @Provides
    @Singleton
    fun provideLocationDao(database: TripDatabase) = database.locationDao()

    @Provides
    @Singleton
    fun provideTripDatabase() = database

    // Retrofit
    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder()
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(oktHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(oktHttpClient)
            .baseUrl("https://api.assettracking.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideReviewService(retrofit: Retrofit): TripService = retrofit.create(TripService::class.java)
}
