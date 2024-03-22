package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class GetCurrentMusicPositionUseCase (private val playbackController: MusicPlaybackController) {

    operator fun invoke() {
        playbackController.getCurrentPosition()

    }
}