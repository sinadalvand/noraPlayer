/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.model.songProvider

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import javax.inject.Inject

class Cursortor @Inject constructor(val resolver: ContentResolver) {

    val URL = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

    fun getAlbumsCursor(): Cursor? {
        return resolver.query(URL, arrayOf(
                "_id",
                MediaStore.Audio.AlbumColumns.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.ARTIST_ID),
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
    }

//    fun getSongsCursor(): Cursor {
//        return resolver.query(URL)
//    }
//
//    fun getArtistCursor(): Cursor {
//        return resolver.query(URL)
//    }

}