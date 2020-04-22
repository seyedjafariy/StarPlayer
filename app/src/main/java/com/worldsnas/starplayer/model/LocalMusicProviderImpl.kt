package com.worldsnas.starplayer.model

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
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

    override suspend fun getAllMusic(): List<LocalMusic> {
        return withContext(IO) {


            val cursor = contentResolver.query(uri, projection, null, null, null, null)
            val localMusics: ArrayList<LocalMusic> = ArrayList()

            cursor?.use {
                cursor.moveToFirst()
                val titleColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val artistColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val albumColumn =
                    cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)

                do {

                    val id = cursor.getInt(idColumn)
                    val title = cursor.getString(titleColumn)
                    val album = cursor.getString(albumColumn)
                    val artist = cursor.getString(artistColumn)

                    val contentUri =
                        ContentUris.withAppendedId(uri, id.toLong()).path
                    if (contentUri.isNullOrEmpty()) {
                        continue
                    } else {
                        val musicModel = LocalMusic(
                            id,
                            title,
                            album,
                            artist,
                            "genre",
                            contentUri
                        )

                        localMusics += musicModel
                    }
                } while (cursor.moveToNext())
            }
            return@withContext localMusics
        }
    }
}


