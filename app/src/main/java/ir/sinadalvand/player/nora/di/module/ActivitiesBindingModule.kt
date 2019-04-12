package ir.sinadalvand.player.nora.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.sinadalvand.player.nora.view.Activities.LandingActivity
import ir.sinadalvand.player.nora.view.Activities.PlayerActivity
import ir.sinadalvand.player.nora.view.Activities.SplashActivity

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun bindLauncherActivity(): LandingActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindPlayerActivity(): PlayerActivity

}