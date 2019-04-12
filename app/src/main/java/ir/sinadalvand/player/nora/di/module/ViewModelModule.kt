package ir.sinadalvand.player.nora.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ir.sinadalvand.player.nora.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.sinadalvand.player.nora.utils.ViewModelFactory
import ir.sinadalvand.player.nora.viewmodel.LandingViewModel
import ir.sinadalvand.player.nora.viewmodel.PlayerViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    internal abstract fun postListViewModel(viewModel: LandingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    internal abstract fun playerViewModel(viewModel: PlayerViewModel): ViewModel

}