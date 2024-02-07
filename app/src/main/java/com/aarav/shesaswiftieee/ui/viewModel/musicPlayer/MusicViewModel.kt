package com.aarav.shesaswiftieee.ui.viewModel.musicPlayer

import androidx.lifecycle.ViewModel
import com.aarav.shesaswiftieee.player.use_case.PauseMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.ResumeMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.SeekMusicPositionUseCase
import com.aarav.shesaswiftieee.player.use_case.SetMusicShuffleEnabledUseCase
import com.aarav.shesaswiftieee.player.use_case.SetPlayerRepeatOneEnabledUseCase
import com.aarav.shesaswiftieee.player.use_case.SkipNextMusicUseCase
import com.aarav.shesaswiftieee.player.use_case.SkipPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val seekMusicPositionUseCase: SeekMusicPositionUseCase,
    private val skipNextMusicUseCase: SkipNextMusicUseCase,
    private val skipPreviousMusicUseCase: SkipPreviousMusicUseCase,
    private val setMusicShuffleEnabledUseCase: SetMusicShuffleEnabledUseCase,
    private val setPlayerRepeatOneEnabledUseCase: SetPlayerRepeatOneEnabledUseCase
) : ViewModel() {
    fun onEvent (event: MusicPlayerEvent){
        when (event) {
            MusicPlayerEvent.ResumeMusic -> resumeMusic()

            MusicPlayerEvent.PauseMusic -> pauseMusic()

            MusicPlayerEvent.SkipNextMusic -> skipNextMusic()

            MusicPlayerEvent.SkipPreviousMusic -> skipPreviousMusic()

            is MusicPlayerEvent.SeekMusicPosition -> seekToMusicPosition(event.musicPosition)

            is MusicPlayerEvent.SetMusicShuffleEnabled -> setMusicShuffleEnabled(event.isShuffleEnabled)

            is MusicPlayerEvent.SetPlayerRepeatOneEnabled -> setPlayerRepeatOneEnabled(event.isRepeatOneEnabled)
        }
    }

     fun resumeMusic() {
        resumeMusicUseCase()
    }

    private fun pauseMusic() {
        pauseMusicUseCase()
    }

    private fun seekToMusicPosition(position: Long) {
        seekMusicPositionUseCase(position)
    }

    private fun skipNextMusic() {
        skipNextMusicUseCase()
    }

    private fun skipPreviousMusic() {
        skipPreviousMusicUseCase()
    }

    private fun setMusicShuffleEnabled(isEnabled: Boolean) {
        setMusicShuffleEnabledUseCase(isEnabled)
    }

    private fun setPlayerRepeatOneEnabled(isRepeatOneEnabled: Boolean) {
        setPlayerRepeatOneEnabledUseCase(isRepeatOneEnabled)
    }

}