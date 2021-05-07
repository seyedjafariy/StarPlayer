package com.worldsnas.starplayer.model

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.worldsnas.starplayer.ConstValues
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
    private val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
    private val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"


    override suspend fun getAllMusic(): List<MusicRepoModel> =
        withContext(IO) {

            val cursor = contentResolver.query(uri, projection, selection, null, null, null)
            val musicRepoModels: ArrayList<MusicRepoModel> = ArrayList()

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
                        val musicModel = MusicRepoModel(
                            id,
                            title,
                            album,
                            artist,
                            "genre",
                            ConstValues.PRE_ADDRESS_VOLUME+ contentUri
                        )

                        musicRepoModels += musicModel
                    }

                } while (cursor.moveToNext())

            }
            musicRepoModels
        }
}


