package com.worldsnas.starplayer.view.music_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.worldsnas.starplayer.model.FakeMusicRepository
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.utils.CoroutinesTestRule
import com.worldsnas.starplayer.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MusicListViewModelTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    lateinit var musicListViewModel: MusicListViewModel

    lateinit var fakeMusicRepository: FakeMusicRepository


    @Before
    fun setup() {
        fakeMusicRepository = FakeMusicRepository()
        musicListViewModel =
            MusicListViewModel(fakeMusicRepository, testCoroutineRule.testDispatcher)
    }


    @Test
    fun `get local musics return not empty list`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeMusicRepository.setFlagReturnEmptyList(false)
            fakeMusicRepository.setFlagReturnError(false)

            musicListViewModel.getMusics()
            Truth.assertThat(musicListViewModel.localMusicList.value)
                .isEqualTo(fakeMusicRepository.getLocalData())
        }


    @Test
    fun `get local musics return empty list`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeMusicRepository.setFlagReturnEmptyList(true)
            fakeMusicRepository.setFlagReturnError(false)

            musicListViewModel.getMusics()
            Truth.assertThat(musicListViewModel.localMusicList.value).isEqualTo(
                Resource.success<List<MusicRepoModel>>(
                    emptyList()
                )
            )
        }

    @Test
    fun `get local musics return error`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeMusicRepository.setFlagReturnEmptyList(false)
            fakeMusicRepository.setFlagReturnError(true)

            musicListViewModel.getMusics()
            Truth.assertThat(musicListViewModel.localMusicList.value).isEqualTo(
                Resource.error("unknown error", null)
            )
        }

    @Test
    fun `get api musics return not empty list`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeMusicRepository.setFlagReturnEmptyList(false)
            fakeMusicRepository.setFlagReturnError(false)

            musicListViewModel.getMusics(1, 10)
            Truth.assertThat(musicListViewModel.musicList.value)
                .isEqualTo(fakeMusicRepository.getApiData(1, 10))
        }

    @Test
    fun `get api musics return empty list`() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            fakeMusicRepository.setFlagReturnEmptyList(true)
            fakeMusicRepository.setFlagReturnError(false)

            musicListViewModel.getMusics(1, 10)
            Truth.assertThat(musicListViewModel.musicList.value).isEqualTo(
                Resource.success(
                    emptyList<MusicRepoModel>()
                )
            )

        }


    @Test
    fun `get api musics return error`() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            fakeMusicRepository.setFlagReturnEmptyList(false)
            fakeMusicRepository.setFlagReturnError(true)

            musicListViewModel.getMusics(1, 10)
            Truth.assertThat(musicListViewModel.musicList.value).isEqualTo(
                Resource.error("unknown error", null)
            )

        }

}
