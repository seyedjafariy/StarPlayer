package com.worldsnas.starplayer

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(JUnit4::class)
class ExampleUnitTest {

    lateinit var musicListViewModel: MusicListViewModel
    lateinit var musicListObserver: Observer<List<MusicRepoModel>>

    @Before
    fun setUp() {
        musicListObserver = mock()
        var musicRepository = mock<MusicRepository>()
        musicListViewModel = MusicListViewModel(musicRepository)

        musicListViewModel.postMusic().observeForever(musicListObserver)
    }

    @Test
    fun testMusicListViewModel() {

        verify(musicListObserver).onChanged(emptyList())

    }
}
