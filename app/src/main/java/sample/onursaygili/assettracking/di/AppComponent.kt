package sample.onursaygili.assettracking.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import sample.onursaygili.assettracking.App
import sample.onursaygili.assettracking.di.module.MainActivityModule
import sample.onursaygili.assettracking.di.module.RoomModule
import sample.onursaygili.assettracking.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    MainActivityModule::class,
    ViewModelModule::class,
    RoomModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun roomModule(roomModule: RoomModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}