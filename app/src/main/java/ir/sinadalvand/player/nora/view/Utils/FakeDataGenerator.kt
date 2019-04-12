/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Utils

import ir.sinadalvand.player.nora.model.songProvider.Song

object FakeDataGenerator {

    fun getFakeSongs():MutableList<Song>{
        val data = arrayListOf<Song>()

        data.add(Song(-1,-1,-1,"Come and Get it","Selena Gomez"))
        data.add(Song(22,-1,-1,"Bang Bang","Dua Lipa"))
        data.add(Song(-1,-1,-1,"I'm an Albatraoz","Little Sis Nora"))
        data.add(Song(-1,-1,-1,"Del Yar","Sara Naeini"))
        data.add(Song(-1,-1,-1,"Dusk Till Down","ZAYN"))
        data.add(Song(-1,-1,-1,"GOT Winterfell","Ramin DJavadi"))
        data.add(Song(-1,-1,-1,"Alaki","Ghomayshi"))
        data.add(Song(-1,-1,-1,"Del Yar","Sara Naeini"))
        data.add(Song(-1,-1,-1,"Dusk Till Down","ZAYN"))
        data.add(Song(-1,-1,-1,"GOT Winterfell","Ramin DJavadi"))
        data.add(Song(-1,-1,-1,"Alaki","Ghomayshi"))


        return data
    }
}