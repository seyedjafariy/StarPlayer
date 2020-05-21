package com.worldsnas.starplayer

import android.content.ContentResolver
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.*
import org.junit.*

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/*
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(JUnit4::class)
class ViewModelUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("Mocked UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testMusicListViewModel() {
        var musicRepository = mock<MusicRepository>()
        var musicListViewModel = MusicListViewModel(musicRepository)
        musicListViewModel.postMusic().observeForever {}

        Assert.assertNull( musicListViewModel.postMusic().value)
    }

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Test
    fun testLocalMusicProviderImpl() =testCoroutineScope.runBlockingTest{
        var contentResolver = mock<ContentResolver>()
        var localMusicProviderImpl = LocalMusicProviderImpl(contentResolver)
        var localMusicList=localMusicProviderImpl.getAllMusic()
        Assert.assertNotNull(localMusicList)
    }

    @After
    fun destroy() {
        Dispatchers.resetMain()
    }
}
