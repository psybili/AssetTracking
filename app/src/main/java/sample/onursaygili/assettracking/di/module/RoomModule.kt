package sample.onursaygili.assettracking.di.module

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import sample.onursaygili.assettracking.App
import sample.onursaygili.assettracking.data.local.TripDao
import sample.onursaygili.assettracking.data.local.TripDatabase
import sample.onursaygili.assettracking.data.TripRepository
import sample.onursaygili.assettracking.data.local.LocationDao
import javax.inject.Singleton

@Module
class RoomModule(app: App) {

    private val database: TripDatabase = Room.databaseBuilder(
            app,
            TripDatabase::class.java,
            "Trips.db"
    ).build()

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, locationDao: LocationDao) = TripRepository(tripDao, locationDao)

    @Provides
    @Singleton
    fun provideTripDao(database: TripDatabase) = database.tripDao()

    @Provides
    @Singleton
    fun provideLocationDao(database: TripDatabase) = database.locationDao()

    @Provides
    @Singleton
    fun provideTripDatabase() = database
}
