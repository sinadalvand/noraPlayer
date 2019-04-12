package ir.sinadalvand.player.nora.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ir.sinadalvand.player.nora.model.songProvider.Song
import javax.inject.Inject

class LandingViewModel @Inject constructor() : ViewModel() {


     val userLiveData = MutableLiveData<MutableList<Song>>()

    fun getData() {
//        userLiveData.postValue(provider.getAllSongs())

    }

}