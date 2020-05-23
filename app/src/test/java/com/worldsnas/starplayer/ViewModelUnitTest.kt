package com.worldsnas.starplayer

import TestCoroutineRule
import android.content.ContentResolver
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/*
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mainThreadSurrogate = newSingleThreadContext("Mocked UI thread")

    @Mock
    private lateinit var musicObserver: Observer<List<MusicRepoModel>>

    @Mock
    private lateinit var musicRepository: MusicRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testMusicListViewModel() {
        testCoroutineRule.runBlockingTest {
            var musicListViewModel = MusicListViewModel(musicRepository)
            musicListViewModel.postMusic().observeForever(musicObserver)
            verify(musicRepository).getLocalData()
            verify(musicObserver).onChanged(emptyList())
        }
    }

    @After
    fun destroy() {
        Dispatchers.resetMain()
    }
}
