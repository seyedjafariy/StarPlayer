package com.worldsnas.starplayer.view.music_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.utils.ConstValues
import com.worldsnas.starplayer.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicListViewModelTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    lateinit var musicListViewModel: MusicListViewModel

    @Mock
    lateinit var musicRepository: MusicRepository

    @Mock
    private lateinit var musicListObserver: Observer<List<MusicRepoModel>>


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `get empty local musics`() {

        testCoroutineRule.runBlockingTest {

            doReturn(emptyList<MusicRepoModel>()).`when`(musicRepository).getLocalData()
            musicListViewModel = MusicListViewModel(musicRepository)


            Truth.assertThat(musicRepository.getLocalData()).isNotNull()
            Truth.assertThat(musicRepository.getLocalData()).isEmpty()

            musicListViewModel.postMusic().observeForever(musicListObserver)


            verify(musicListObserver, times(1)).onChanged(emptyList())
            musicListViewModel.postMusic().removeObserver(musicListObserver)

        }

    }


    @Test
    fun `get not empty local musics`() {
        testCoroutineRule.runBlockingTest {
            val musics = arrayOf(
                LOCAL_MUSIC_ONE, LOCAL_MUSIC_TWO, LOCAL_MUSIC_THREE,
                LOCAL_MUSIC_FOUR, LOCAL_MUSIC_FIVE
            ).toList()
            doReturn(musics).`when`(musicRepository).getLocalData()

            musicListViewModel = MusicListViewModel(musicRepository)

            verify(musicRepository, times(1)).getLocalData()

            Truth.assertThat(musicRepository.getLocalData()).isNotEmpty()

            musicListViewModel.postMusic().observeForever(musicListObserver)

            verify(musicListObserver, times(1)).onChanged(musics)

            musicListViewModel.postMusic().removeObserver(musicListObserver)


        }


    }


    @Test
    fun `check validate local music address`() {

        testCoroutineRule.runBlockingTest {
            val musics = arrayOf(
                LOCAL_MUSIC_ONE, LOCAL_MUSIC_TWO, LOCAL_MUSIC_THREE,
                LOCAL_MUSIC_FOUR, LOCAL_MUSIC_FIVE
            ).toList()
            doReturn(musics).`when`(musicRepository).getLocalData()

            musicListViewModel = MusicListViewModel(musicRepository)

            Truth.assertThat(musicRepository.getLocalData()).isNotEmpty()

            assertThat(
                musicRepository.getLocalData(), hasItem(
                    hasProperty(
                        "address",
                        startsWith(ConstValues.PRE_ADDRESS_VOLUME)
                    )
                )
            )

        }

    }


    @Test
    fun `get api musics`() {
        testCoroutineRule.runBlockingTest {

            val apiMusics = arrayOf(
                API_MUSIC_ONE, API_MUSIC_TWO, API_MUSIC_THREE, API_MUSIC_FOUR,
                API_MUSIC_FIVE
            ).toList()

            doReturn(apiMusics).`when`(musicRepository).getApiData(PAGE, COUNT)

            musicListViewModel = MusicListViewModel(musicRepository)

            musicListViewModel.getMusics(PAGE, COUNT)

            musicListViewModel.postMusic().observeForever(musicListObserver)


            Truth.assertThat(musicRepository.getApiData(PAGE, COUNT)).hasSize(5)

            verify(musicListObserver, times(1)).onChanged(apiMusics)



            assertThat(musicRepository.getApiData(PAGE, COUNT), hasItem(hasProperty("address",
                startsWith("https://"))))

            musicListViewModel.postMusic().removeObserver(musicListObserver)

        }


    }

    companion object {


        const val PAGE = 1
        const val COUNT = 5

        private val API_MUSIC_ONE = MusicRepoModel(
            1,
            "Sorry",
            "Justin Bieber",
            "Purpose",
            "genre",
            "https://" + 1
        )
        private val API_MUSIC_TWO = MusicRepoModel(
            2, "Diamonds", "Rihanna", "Diamonds-Single",
            "genre",
            "https://" + 2
        )

        private val API_MUSIC_THREE = MusicRepoModel(
            3, "Everyday Is Christmas", "Sia", "Everyday Is Christmas",
            "genre", "https://" + 3
        )
        private val API_MUSIC_FOUR = MusicRepoModel(
            4, "Lonely Together", "Sofia Karlberg", "Lonely Together",
            "genre",
            "https://" + 4
        )
        private val API_MUSIC_FIVE = MusicRepoModel(
            5, "Someone Like You", "Adele", "21",
            "genre",
            "https://" + 5
        )


        private val LOCAL_MUSIC_ONE = MusicRepoModel(
            1,
            "Sorry",
            "Justin Bieber",
            "Purpose",
            "genre",
            ConstValues.PRE_ADDRESS_VOLUME + 1
        )
        private val LOCAL_MUSIC_TWO = MusicRepoModel(
            2, "Diamonds", "Rihanna", "Diamonds-Single",
            "genre",
            ConstValues.PRE_ADDRESS_VOLUME + 2
        )

        private val LOCAL_MUSIC_THREE = MusicRepoModel(
            3, "Everyday Is Christmas", "Sia", "Everyday Is Christmas",
            "genre", ConstValues.PRE_ADDRESS_VOLUME + 3
        )
        private val LOCAL_MUSIC_FOUR = MusicRepoModel(
            4, "Lonely Together", "Sofia Karlberg", "Lonely Together",
            "genre",
            ConstValues.PRE_ADDRESS_VOLUME + 4
        )
        private val LOCAL_MUSIC_FIVE = MusicRepoModel(
            5, "Someone Like You", "Adele", "21",
            "genre",
            ConstValues.PRE_ADDRESS_VOLUME + 5
        )

    }

}