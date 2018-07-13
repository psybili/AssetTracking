package sample.onursaygili.assettracking.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.onursaygili.assettracking.ui.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ServiceBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
