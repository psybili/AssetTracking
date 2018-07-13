package sample.onursaygili.assettracking.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import sample.onursaygili.assettracking.data.LocationUpdatesService;

@Module
abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract LocationUpdatesService contributeLocationUpdatesService();

}