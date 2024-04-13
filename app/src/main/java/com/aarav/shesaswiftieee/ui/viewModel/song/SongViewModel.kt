package com.aarav.shesaswiftieee.ui.viewModel.song

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarav.shesaswiftieee.repository.FireRepository
import com.aarav.shesaswiftieee.data.DataOrException
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.player.use_case.AddMediaItemsUseCase
import com.aarav.shesaswiftieee.player.use_case.ClearMediaItemsUseCase
import com.aarav.shesaswiftieee.player.use_case.PauseMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.PlayMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val clearMediaItemsUseCase: ClearMediaItemsUseCase,
    private val fireRepository: FireRepository,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: PauseMusicUseCase
) : ViewModel() {

    val songData: MutableState<DataOrException<List<SWIFT>, Boolean, Exception>> =
        mutableStateOf(DataOrException(listOf(), true, Exception("")))

    var homeUiState by mutableStateOf(HomeUiState())
        private set


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.PauseMusic -> pauseMusic()
            HomeEvent.PlayMusic -> playMusic()
            HomeEvent.ResumeMusic -> resumeMusic()

            is HomeEvent.OnMusicSelected -> {
                homeUiState = homeUiState.copy(selectedMusic = event.selectedMusic)
            }
        }
    }


    init {
        viewModelScope.launch(Dispatchers.Main) {
            getAllSongs()
        }
    }

    private fun getAllSongs() {
        homeUiState = homeUiState.copy(loading = true)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                songData.value.loading = true // Set loading to true before the request
                val result = fireRepository.getAllSongsFromDataBase("SWIFT")
                result.data = result.data
                songData.value = result
                addMediaItemsUseCase(songData.value.data!!)
                homeUiState = homeUiState.copy(
                    loading = false, musics = songData.value.data!!
                )

            } catch (exception: Exception) {
                songData.value.e = exception

                homeUiState = homeUiState.copy(
                    loading = false, errorMessage = exception.toString()
                )

                Log.e("SOOCK", "look mom the view model is faulty ", exception)
            } finally {
                songData.value.loading = false // Set loading to false after the request
                homeUiState = homeUiState.copy(loading = false)
            }
        }
    }

    fun searchAlbum(albumName: String): List<SWIFT> {

        val song = mutableListOf<SWIFT>()

        if (!songData.value.data.isNullOrEmpty()) {
            songData.value.data!!.forEach {
                if ((it.album).equals(albumName)) {
                    song.add(it)
                }
            }
        }

        return song.sortedBy { it.mediaID!!.toInt() }
    }

    private fun playMusic() {
        homeUiState.apply {
            musics?.indexOf(selectedMusic)?.let {
                playMusicUseCase(it)
            }
        }
    }

    private fun resumeMusic() {
        resumeMusicUseCase()
    }

    private fun pauseMusic() {
        pauseMusicUseCase()
    }

    fun addMediaItemsByAlbum(albumName: String) {
        val data = searchAlbum(albumName)
            clearMediaItemsUseCase()
            addMediaItemsUseCase(data)
        homeUiState.musics = data
    }

}