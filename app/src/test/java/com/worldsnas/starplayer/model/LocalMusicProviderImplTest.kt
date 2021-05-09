package com.worldsnas.starplayer.model

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.nhaarman.mockitokotlin2.doReturn
import org.junit.Before
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboCursor

@RunWith(RobolectricTestRunner::class)
class LocalMusicProviderImplTest {

    private lateinit var audioCursor: RoboCursor
    private lateinit var contentResolver: ContentResolver
    private lateinit var localMusicProviderImpl: LocalMusicProviderImpl

    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM
    )
    private val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
    private val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

    @Before
    fun setUp() {
        audioCursor = RoboCursor()

        contentResolver = mock {
            on {
                query(uri, projection, selection, null, null, null)
            } doReturn audioCursor
        }
        audioCursor.setColumnNames(
            AUDIO_COLUMNS
        )

        audioCursor.setResults(
            arrayOf(
                MUSIC_1,
                MUSIC_2,
                MUSIC_3,
                MUSIC_4,
                MUSIC_5
            )
        )

        localMusicProviderImpl = LocalMusicProviderImpl(contentResolver)
    }

    @Test
    fun `check music list is not empty`() = runBlocking {


        val localMusics = localMusicProviderImpl.getAllMusic()

        assertThat(
            "musicList is empty",
            localMusics,
            CoreMatchers.`is`(CoreMatchers.not(emptyList()))
        )

    }

    @Test
    fun `check music list size`() = runBlocking {

        val localMusics = localMusicProviderImpl.getAllMusic()

        assertThat("musicList's size is wrong", localMusics.size, CoreMatchers.`is`(5))
    }


    companion object MockData {
        private val AUDIO_COLUMNS = listOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM
        )

        private val MUSIC_1 = arrayOf(
            1, "Justin Bieber", "Sorry", "Purpose"
        )
        private val MUSIC_2 = arrayOf(
            2, "Rihanna", "Diamonds", "Diamonds-Single"
        )
        private val MUSIC_3 = arrayOf(
            3, "Sia", "Everyday Is Christmas", "Everyday Is Christmas"
        )
        private val MUSIC_4 = arrayOf(
            4, "Sofia Karlberg", "Lonely Together", "Lonely Together"
        )
        private val MUSIC_5 = arrayOf(
            5, "Adele", "Someone Like You", "21"
        )

    }
}