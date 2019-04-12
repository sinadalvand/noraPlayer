package ir.sinadalvand.player.nora.model.songProvider

data class Song(val id: Long = -1,
                val albumId: Long = -1,
                val artistId: Long = -1,
                val title: String = "",
                val artistName: String = "",
                val albumName: String = "",
                val duration: Int = -1,
                val trackNumber: Int = -1)


