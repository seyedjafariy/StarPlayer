package com.worldsnas.starplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(JUnit4::class)
class ExampleUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("Mocked UI thread")

    lateinit var musicListViewModel: MusicListViewModel
    lateinit var musicListObserver: Observer<List<MusicRepoModel>>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        musicListObserver = mock()
    }

    @Test
    fun testMusicListViewModel() {
        var musicRepository = mock<MusicRepository>()
        musicListViewModel = MusicListViewModel(musicRepository)

        val listClass = ArrayList::class.java as Class<List<MusicRepoModel>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)

        musicListViewModel.postMusic().observeForever(musicListObserver)

        verify(musicListObserver).onChanged(argumentCaptor.capture())

    }
}
