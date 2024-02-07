package com.aarav.shesaswiftieee.ui.viewModel.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.player.use_case.DestroyMediaControllerUseCase
import com.aarav.shesaswiftieee.player.use_case.GetCurrentMusicPositionUseCase
import com.aarav.shesaswiftieee.player.use_case.SetMediaControllerCallbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val setMediaControllerCallbackUseCase: SetMediaControllerCallbackUseCase,
    private val getCurrentMusicPositionUseCase: GetCurrentMusicPositionUseCase,
    private val destroyMediaControllerUseCase: DestroyMediaControllerUseCase
) : ViewModel() {
    var musicPlaybackState by mutableStateOf(MusicPlaybackUIState())
        private set

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        setMediaControllerCallbackUseCase { playerState, currentMusic, currentPosition, totalDuration, isShuffleEnabled, isRepeatOneEnabled ->

            musicPlaybackState = musicPlaybackState.copy(
                playerState = playerState,
                currentMusic = currentMusic,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                isShuffleEnabled = isShuffleEnabled,
                isRepeatOneEnabled = isRepeatOneEnabled
            )

            if (playerState == PlayerState.PLAYING){
                viewModelScope.launch {
                    while (true){
                        delay(1.seconds)
                        musicPlaybackState = musicPlaybackState.copy(
                            currentPosition = getCurrentMusicPositionUseCase().toString().toLong()
                        )
                    }
                }
            }
        }
    }

    fun destroyMediaController(){
        destroyMediaController()
    }
}