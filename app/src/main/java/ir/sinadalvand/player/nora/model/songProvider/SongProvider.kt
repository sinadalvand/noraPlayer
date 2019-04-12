package ir.sinadalvand.player.nora.model.songProvider

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.provider.MediaStore
import javax.inject.Inject

class SongProvider @Inject constructor(val context: Context) {

    private val sEmptyList = LongArray(0)

    fun getAllSongs(): ArrayList<Song> {
        return getSongsForCursor(cursorBuilder())
    }


    fun getSongsForCursor(cursor: Cursor?): ArrayList<Song> {
        val arrayList = ArrayList<Song>()
        if (cursor != null && cursor.moveToFirst())
            do {
                val id = cursor.getLong(0)
                val title = cursor.getString(1)
                val artist = cursor.getString(2)
                val album = cursor.getString(3)
                val duration = cursor.getInt(4)
                val trackNumber = cursor.getInt(5)
                val artistId = cursor.getInt(6).toLong()
                val albumId = cursor.getLong(7)

                arrayList.add(Song(id, albumId, artistId, title, artist, album, duration, trackNumber))
            } while (cursor.moveToNext())
        cursor?.close()
        return arrayList
    }

    fun getSongForCursor(cursor: Cursor?): Song {
        var song = Song()
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getLong(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val album = cursor.getString(3)
            val duration = cursor.getInt(4)
            val trackNumber = cursor.getInt(5)
            val artistId = cursor.getInt(6).toLong()
            val albumId = cursor.getLong(7)

            song = Song(id, albumId, artistId, title, artist, album, duration, trackNumber)
        }

        cursor?.close()
        return song
    }

    fun getSongListForCursor(cursor: Cursor?): LongArray {
        cursor?.let { return sEmptyList }
        val len = cursor!!.count
        val list = LongArray(len)
        cursor.moveToFirst()
        var columnIndex = -1
        try {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID)
        } catch (notaplaylist: IllegalArgumentException) {
            columnIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
        }
        for (i in 0 until len) {
            list[i] = cursor.getLong(columnIndex)
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun getSongFromPath(songPath: String, context: Context): Song {
        val cr = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.DATA
        val selectionArgs = arrayOf(songPath)
        val projection = arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id")
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = cr.query(uri, projection, "$selection=?", selectionArgs, sortOrder)
        return if (cursor != null && cursor.count > 0) {
            val song = getSongForCursor(cursor)
            cursor.close()
            song
        } else {
            Song()
        }

    }


    fun cursorBuilder(selectionStatement: String = "is_music=1 AND title != ''", paramArrayOfString: Array<String>? = null, sort: String = MediaStore.Audio.Media.DEFAULT_SORT_ORDER): Cursor? {
        return context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"), selectionStatement, paramArrayOfString, sort)
    }

}