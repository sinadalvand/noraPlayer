package ir.sinadalvand.player.nora.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import ir.sinadalvand.player.nora.CoolPlayer
import ir.sinadalvand.player.nora.di.module.ActivitiesBindingModule
import ir.sinadalvand.player.nora.di.module.PlayerModule
import ir.sinadalvand.player.nora.di.module.ViewModelModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, PlayerModule::class, ActivitiesBindingModule::class, ViewModelModule::class])
interface PlayerComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PlayerComponent
    }

    fun inject(app: CoolPlayer)

}