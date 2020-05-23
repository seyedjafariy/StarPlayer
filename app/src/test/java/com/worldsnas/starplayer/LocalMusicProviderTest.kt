package com.worldsnas.starplayer

import android.content.ContentResolver
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class LocalMusicProviderTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Test
    fun testLocalMusicProviderImpl() =testCoroutineScope.runBlockingTest{
//        var contentResolver = mock<ContentResolver>()
//        var localMusicProviderImpl = LocalMusicProviderImpl(contentResolver)
//        var localMusicList=localMusicProviderImpl.getAllMusic()
//        Assert.assertNotNull(localMusicList)
    }
}