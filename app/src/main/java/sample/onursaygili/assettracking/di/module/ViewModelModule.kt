package sample.onursaygili.assettracking.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import sample.onursaygili.assettracking.di.TrackingViewModelFactory
import sample.onursaygili.assettracking.di.ViewModelKey
import sample.onursaygili.assettracking.ui.tracking.TrackingViewModel
import sample.onursaygili.assettracking.ui.triplist.TripListViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrackingViewModel::class)
    abstract fun bindTrackingViewModel(viewModel: TrackingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripListViewModel::class)
    abstract fun bindTripListViewModel(viewModel: TripListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TrackingViewModelFactory): ViewModelProvider.Factory
}
