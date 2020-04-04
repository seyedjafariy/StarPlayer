package com.worldsnas.starplayer.model

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalMusicProviderImpl @Inject
constructor(private val contentResolver: ContentResolver) : LocalMusicProvider {


    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM
    )
    private val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    override suspend fun getAllMusic(): List<Music> {
        return withContext(IO) {


            val cursor = contentResolver.query(uri, projection, null, null, null, null)
            val musics: ArrayList<Music> = ArrayList()

            cursor?.use {
                val titleColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Genres.Members.TITLE)
                val idColumn = cursor.getColumnIndex(MediaStore.Audio.Genres.Members._ID)
                val artistColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ARTIST)
                val albumColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Genres.Members.ALBUM)

                do {

                    val id = cursor.getInt(idColumn)
                    val title = cursor.getString(titleColumn)
                    val album = cursor.getString(albumColumn)
                    val artist = cursor.getString(artistColumn)
                    val contentUri: Uri =
                        ContentUris.withAppendedId(uri, id.toLong())

                    val musicModel = Music(
                        id,
                        title,
                        album,
                        artist,
                        "genre",
                        contentUri.path.toString()
                    )

                    musics += musicModel
                } while (cursor.moveToNext())
            }
            return@withContext musics
        }
    }
}


