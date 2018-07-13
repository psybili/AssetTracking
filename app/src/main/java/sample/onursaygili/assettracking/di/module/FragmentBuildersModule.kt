package sample.onursaygili.assettracking.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.onursaygili.assettracking.ui.tracking.TrackingFragment
import sample.onursaygili.assettracking.ui.triplist.TripListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeTrackingFragment(): TrackingFragment

    @ContributesAndroidInjector
    abstract fun contributeTripListFragment(): TripListFragment
}