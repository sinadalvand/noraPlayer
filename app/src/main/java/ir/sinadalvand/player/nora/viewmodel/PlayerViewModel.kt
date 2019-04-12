/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ir.sinadalvand.player.nora.model.songProvider.Song
import javax.inject.Inject

class PlayerViewModel @Inject constructor() : ViewModel() {


     val userLiveData = MutableLiveData<MutableList<Song>>()

    fun getData() {
//        userLiveData.postValue(provider.getAllSongs())

    }

}